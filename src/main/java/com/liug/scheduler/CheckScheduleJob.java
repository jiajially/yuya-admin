package com.liug.scheduler;

import com.liug.common.util.FileUtil;
import com.liug.dao.CheckMapper;
import com.liug.dao.MonitorLogMapper;
import com.liug.dao.SshHostMapper;
import com.liug.model.entity.Check;
import com.liug.model.entity.MonitorLog;
import com.liug.model.entity.SshHost;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liugang on 2017/7/10.
 * 内存监控
 * 一分钟1次
 */
@Component
public class CheckScheduleJob{

    private static final Logger logger = LoggerFactory.getLogger(CheckScheduleJob.class);

    @Autowired
    SshHostMapper sshHostMapper;

    @Autowired
    MonitorLogMapper monitorLogMapper;

    @Autowired
    CheckMapper checkMapper;

    @Async
    public void initFileData() throws ParseException {
        long start = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin  = sdf.parse("2005-01-01");
        Date end  = sdf.parse("2017-09-01");
        SshHost sshHost = new SshHost();
        while (begin.before(end)){
            logger.info("现在处理:"+sdf.format(begin));
            initCheckResult(sshHost,begin);
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            begin=c.getTime();

        }
        long stop = System.currentTimeMillis();
        logger.debug("例行检查>>>>>耗时："+(stop - start)+"ms");
    }

    /**
     *
     *例行检查执行框架
     *
     */
    @Async
    void doJob(){
        long start = System.currentTimeMillis();
        SshHost sshHost = new SshHost();

        //checkResult(sshHost);

        long stop = System.currentTimeMillis();
        logger.debug("例行检查>>>>>耗时："+(stop - start)+"ms");
    }


    /**
     * Purpose:例行检查执行接口
     */
    public void checkResult(SshHost sshHost){
        String result=null;

        Date exectime = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(exectime);
        String begin_str = ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);

        Check check = new Check();
        check.setExectime(exectime);


        switch ("backup"){
            case "backup":{

            }
            case "error":{

            }
            case "database":{

            }
            case "batch":{

            }
            case "performance":{

            }
            case "space":{

            }
            default:break;
        }
    }


    /**
     * Purpose:例行检查结果历史数据初始化
     */
    public void initCheckResult(SshHost sshHost, Date exectime){
        String result=null;

        Calendar c = Calendar.getInstance();
        c.setTime(exectime);
        String begin_str = ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);

        Check check = new Check();
        check.setExectime(exectime);


        switch ("backup"){
            case "backup":{
                result = doCheck(sshHost,"bu",begin_str);
                check.setType("backup");
                check.setSummary("例行检查-备份-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            case "error":{
                result = doCheck(sshHost,"er",begin_str);
                check.setType("error");
                check.setSummary("例行检查-错误检查-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            case "database":{
                result = doCheck(sshHost,"db",begin_str);
                check.setType("database");
                check.setSummary("例行检查-数据库空间-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            case "batch":{
                result = doCheck(sshHost,"batch",begin_str);
                check.setType("batch");
                check.setSummary("例行检查-批处理-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            case "performance":{
                result = doCheck(sshHost,"p",begin_str);
                check.setType("performance");
                check.setSummary("例行检查-系统性能-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            case "space":{
                result = doCheck(sshHost,"b",begin_str);
                check.setType("space");
                check.setSummary("例行检查-空间检查-" + begin_str);
                check.setResult(result);
                if (result!=null)checkMapper.insert(check);
            }
            default:break;
        }
    }

    //执行例行检查,目前接口未知，模拟出结果
    private String doCheck(SshHost sshHost, String type, String exectime){
        String path = FileUtil.getProjectPath()+"/file/"+type+"/"+type+exectime+".htm";
        String tmp = null;
        try {
            File file=new File(path);
            logger.info(path);
            if (file.exists()) {
                InputStream is = new FileInputStream(new File(path));
                tmp = IOUtils.toString(is, "GB2312");
            }else tmp=null;
        } catch (IOException e) {

        }
        return tmp;
    }

}
