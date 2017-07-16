package com.liug.common.resources;

/**
 * Created by liugang on 2017/7/12.
 */
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liug.common.ssh.Commond;
import com.liug.model.entity.SshHost;
import org.apache.log4j.Logger;

/**
 * 采集CPU使用率
 */
public class CpuUsage {

    private static CpuUsage INSTANCE = new CpuUsage();

    private CpuUsage(){

    }

    public static CpuUsage getInstance(){
        return INSTANCE;
    }

    /**
     * Purpose:采集CPU使用率
     * @return float,CPU使用率,小于1
     */
    public float get() {

        SshHost host = new SshHost();
        host.setEnvPath("/home/runtime/monitor/java/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/home/runtime/maven/bin:/home/runtime/jdk7/bin:/bin:/root/bin");
        host.setHost("192.168.31.188");
        host.setPassword("117700");
        host.setUsername("liugang");
        host.setPort(22);



        float cpuUsage = 0;
            String command = "cat /proc/stat";
            //第一次采集CPU时间
            //long startTime = System.currentTimeMillis();
            Map<String,Long> time1 = getCpuTime(host,command);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            //第二次采集CPU时间
            //long endTime = System.currentTimeMillis();
            Map<String,Long> time2 = getCpuTime(host,command);

            //计算
        long idleCpuTime1 = time1.get("IdleCpuTime"), totalCpuTime1 = time1.get("TotalCpuTime");
        long idleCpuTime2 = time2.get("IdleCpuTime"), totalCpuTime2 = time2.get("TotalCpuTime");
            if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){
                cpuUsage = 1 - (float)(idleCpuTime2 - idleCpuTime1)/(float)(totalCpuTime2 - totalCpuTime1);
                System.out.println("本节点CPU使用率为: " + cpuUsage);
            }

        return cpuUsage;
    }

    Map<String,Long> getCpuTime(SshHost sshHost,String command){
        Map<String,Long> time = new HashMap<String,Long>();
        String res= Commond.execute(sshHost,command).getContent();
        long idleCpuTime = 0, totalCpuTime = 0;	//分别为系统启动后空闲的CPU时间和总的CPU时间
        String[] resArray = res.split("\n");
        System.out.println(resArray.length);
        for (String resLine:resArray) {
            if(resLine.startsWith("cpu")){
                resLine = resLine.trim();
                System.out.println(resLine);
                String[] temp = resLine.split("\\s+");
                idleCpuTime = Long.parseLong(temp[4]);
                for(String s : temp){
                    if(!s.equals("cpu")){
                        totalCpuTime += Long.parseLong(s);
                    }
                }
                time.put("IdleCpuTime",idleCpuTime);
                time.put("TotalCpuTime",totalCpuTime);
                System.out.println("IdleCpuTime: " + idleCpuTime + ", " + "TotalCpuTime：" + totalCpuTime);
                break;
            }
        }
        return time;
    }
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while(true){
            System.out.println(CpuUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }

}