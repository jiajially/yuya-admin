
package com.liug.common.ssh;
/**
 * 会话池的设计思路
 * 1、
 * 初始化固定数目的会话（空闲会话与活跃会话在同一池中），建立会话的代理类，添加busy与startTime属性以便分发与回收会话
 * 另建立守护线程回收失效会话
 * 2、
 * 维护一空闲会话池，初始为空，需要会话时建立，用完的会话回收进入空闲会话池；
 * 后续所需会话从空闲会话池获取；activeNum记录活跃会话数目；
 * 当空闲会话池为空且活跃会话数达到上限时，请求等待，超时即获取会话失败，超时前有会话被释放方可获得会话
 * 第二个设计巧妙优势明显，采用第二种方式
 *
 * 数据库会话管理类，单例模式
 * 可以管理加载多个数据库驱动，维护多个数据库会话池
 * @author liug
 *
 */
import com.jcraft.jsch.Session;
import com.liug.dao.SshHostMapper;
import com.liug.model.entity.SshHost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

public class SshSessionManager {

    private static SshSessionManager dbm = null;


    //单例模式里的成员变量都相当于是static了？
    /**
     * 客户数目
     */
    private static int clients = 0;

    //主机信息
    private static List<SshHost> sshHosts  =new ArrayList<SshHost>();
    /**
     * 数据库会话池字典
     */
    private Hashtable<String,SshSessionPool> pools = new Hashtable<String,SshSessionPool>();

    private final Logger log = LoggerFactory.getLogger(SshSessionPool.class);

    private SshSessionManager(){
        log.info("创建单例");
    }


    /**
     * 根据配置文件创建数据库会话池
     */
    private void initPool(SshHost sshHost) {
        String poolName = sshHost.getUsername()+"@"+sshHost.getHost()+":"+sshHost.getPort();
        if (!pools.containsKey(poolName)){
            SshSessionPool pool = new SshSessionPool(10,poolName,sshHost);
            pools.put(poolName, pool);
            log.info("成功Linux会话池：" + poolName);
        }
        sshHosts.add(sshHost);

    }

    /**
     * 获得单例
     * @return DBConnectionManager单例
     */
    public synchronized static SshSessionManager getInstance() {
        if(dbm == null) {
            dbm = new SshSessionManager();
        }
        clients++;
        return dbm;
    }

    /**
     * 从指定会话池中获取可用会话，无等待
     * @param poolName	要获取会话的会话池名称
     * @return	会话池中的一个可用会话或null
     */
    public Session getSession(String poolName) {
        SshSessionPool pool = (SshSessionPool)pools.get(poolName);
        return pool.getSession();
    }

    /**
     * 从指定会话池中获取可用会话，无等待
     * @param sshHost	要获取会话的会话池名称
     * @return	会话池中的一个可用会话或null
     */
    public Session getSession(SshHost sshHost) {
        initPool(sshHost);
        String poolName = sshHost.getUsername()+"@"+sshHost.getHost()+":"+sshHost.getPort();
        return getSession(poolName);
    }

    /**
     * 从指定的会话池获取可用会话，有等待超时
     * @param poolName	要获取会话的会话池名称
     * @param timeout	获取可用会话的超时时间,单位为秒
     * @return			会话池中的一个可用会话或null
     */
    public Session getSession(String poolName,long timeout) {
        SshSessionPool  pool = (SshSessionPool)pools.get(poolName);
        return pool.getSession(timeout);
    }

    /**
     * 回收指定会话池的会话
     * @param poolName	会话池名称
     * @param session	要回收的会话
     */
    public void freeSession(String poolName,Session session) {
        SshSessionPool pool = (SshSessionPool)pools.get(poolName);
        if(pool != null) {
            pool.freeSession(session);clients--;
        }
        else log.error("找不到会话池，无法回收，请检查参数");
    }
    /**
     * 回收指定会话池的会话
     * @param sshHost	会话池主机信息
     * @param session	要回收的会话
     */
    public void freeSession(SshHost sshHost,Session session) {
        String poolName = sshHost.getUsername()+"@"+sshHost.getHost()+":"+sshHost.getPort();
        freeSession(poolName,session);

    }

    /**
     * 关闭所有会话
     */
    public synchronized void release() {
        //所有客户会话都关闭时才真正关闭会话撤销注册
        System.out.println(clients);
        if(--clients != 0) {
            return;
        }
        for(Entry<String,SshSessionPool> poolEntry:pools.entrySet()) {
            SshSessionPool pool = poolEntry.getValue();
            pool.releaseAll();
        }
        log.info("已经关闭所有会话");
    }

    /**
     * 此内部类定义了一个会话池.
     * 它能够获取数据库会话,直到预定的最 大会话数为止
     * 在返回会话给客户程序之前,它能够验证会话的有效性
     * @author shijin
     */
    private class SshSessionPool {
        private int activeNum = 0;
        private int maxConn = 0;
        private String poolName = null;
        private SshHost sshHost = null;
        private List<Session> freeSessions = new ArrayList<Session>();

        /**
         *
         * @param maxConn	设定的会话池允许的最大会话数
         * @param sshHost		SSH host
         */
        public SshSessionPool(int maxConn,String poolName, SshHost sshHost) {
            super();
            this.maxConn = maxConn;
            this.poolName = poolName;
            this.sshHost = sshHost;
        }

        /**
         * 获得一个可用回话，不保证任何情况都能返回一个会话（比如超过最大会话数的时候返回null）
         * 1、若空闲会话池不为空，从空闲会话池取出一个会话后检查有效性，正常则返回，失效则重新获取会话
         * 2、若空闲会话池为空且未超过最大会话数限制，新建一个会话并返回
         * 3、空闲会话数为空且超过最大会话数限制，返回null
         * @return	获得的可用会话
         */
        public synchronized Session getSession() {
            Session session = null;
            //空闲会话池中有空闲会话，直接取
            if(freeSessions.size() > 0) {
                //从空闲会话池中取出一个会话
                session = freeSessions.get(0);
                freeSessions.remove(0);
                //检测会话有效性
                try{
                    if(!session.isConnected()) {
                        //由于已经从空闲会话池取出，所以不使用无效会话其就无法重新进入
                        //空闲会话池，意味着其已经被删除了，记入日志即可
                        log.info("从会话池" + poolName + "中取出的会话已关闭，重新获取会话");
                        //继续从会话池尝试获取会话
                        session = getSession();
                    }
                }catch(Exception e) {
                    log.info("从会话池" + poolName + "中取出的发生服务器访问错误，重新获取会话");
                    session = getSession();
                }
            } else if(activeNum < maxConn) {
                session = newSession();
            } else {
                //未获得会话
            }
            if(session != null) {
                activeNum++;
            }
            return session;
        }

        /**
         * 当无空闲会话而又未达到最大会话数限制时创建新的会话
         * @return	新创建的会话
         */
        private Session newSession() {
            JSch jsch=new JSch();
            Session session = null;
            try{
                session=jsch.getSession(sshHost.getUsername(), sshHost.getHost(), sshHost.getPort());
                session.setPassword(sshHost.getPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                log.info("与Linux" + poolName + "创建一个新会话");
            }catch(JSchException e) {
                log.error("无法根据\"" + sshHost.toString() + "\"与Linux" + poolName + "建立新会话");
            }
            return session;
        }

        /**
         * 获得一个可用会话，超过最大会话数时线程等待，直到有有会话释放时返回一个可用会话或者超时返回null
         * @param timeout 等待会话的超时时间，单位为秒
         * @return
         */
        public synchronized Session getSession(long timeout) {
            Session session = null;
            long startTime = System.currentTimeMillis();
            while((session = getSession()) == null) {
                try{
                    //被notify(),notifyALL()唤醒或者超时自动苏醒
                    wait(timeout);
                }catch(InterruptedException e) {
                    log.error("等待会话的线程被意外打断");
                }
                //若线程在超时前被唤醒，则不会返回null，继续循环尝试获取会话
                if(System.currentTimeMillis() - startTime > timeout*1000000)
                    return null;
            }
            return session;
        }

        /**
         * 将释放的空闲会话加入空闲会话池，活跃会话数减一并激活等待会话的线程
         * @param session	释放的会话
         */
        public synchronized void freeSession(Session session) {
            freeSessions.add(session);
            activeNum--;
            notifyAll();//通知正在由于达到最大会话数限制而wait的线程获取会话
        }

        /**
         * 关闭空闲会话池中的所有会话
         */
        public synchronized void releaseAll() {
            for(Session session:freeSessions) {
                try{
                    session.disconnect();
                    log.info("关闭空闲会话池" + poolName + "中的一个会话");
                }catch(Exception e) {
                    log.error("关闭空闲会话池" + poolName + "中的会话失败");
                }
            }
            freeSessions.clear();
        }
    }
}