package com.liug.scheduler;

import com.liug.common.ssh.Commond;
import com.liug.dao.MonitorLogMapper;
import com.liug.dao.SshHostMapper;
import com.liug.model.entity.MonitorLog;
import com.liug.model.entity.SshHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liugang on 2017/7/10.
 * 内存监控
 * 一分钟1次
 */
@Component
public class CpuScheduleJob extends ScheduleJob {

    private static final Logger logger = LoggerFactory.getLogger(CpuScheduleJob.class);

    @Autowired
    SshHostMapper sshHostMapper;

    @Autowired
    MonitorLogMapper monitorLogMapper;

    @Async
    void doJob(Long hostId,Long jobId){
        long start = System.currentTimeMillis();
        logger.debug("CPU监控开始>>>>>hostId:"+hostId+">>>>jobId:"+jobId);
        //Step1 获取主机信息
        SshHost sshHost = sshHostMapper.selectById(hostId);
        //Step2 透过主机信息获取内存数据
        //内存占比获取脚本如下
        //free  | sed -n '2p' | awk '{print $3/$2}'
        String result = String.valueOf(this.get(sshHost,"cat /proc/stat"));
        MonitorLog monitorLog = new MonitorLog();
        monitorLog.setHostId(hostId);
        monitorLog.setJobId(jobId);
        monitorLog.setResult(result);
        monitorLogMapper.insert(monitorLog);
        long end = System.currentTimeMillis();
        logger.debug("CPU监控监控>>>>>hostId:"+hostId+">>>>jobId:"+jobId+">>>>耗时："+(end - start)+"ms");
    }

    /**
     * Purpose:采集CPU使用率
     * @return float,CPU使用率,小于1
     */
    public float get(SshHost sshHost,String command) {
        float cpuUsage = 0;
        //第一次采集CPU时间
        //long startTime = System.currentTimeMillis();
        Map<String,Long> time1 = getCpuTime(sshHost,command);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
        //第二次采集CPU时间
        //long endTime = System.currentTimeMillis();
        Map<String,Long> time2 = getCpuTime(sshHost,command);

        //计算
        long idleCpuTime1 = time1.get("IdleCpuTime"), totalCpuTime1 = time1.get("TotalCpuTime");
        long idleCpuTime2 = time2.get("IdleCpuTime"), totalCpuTime2 = time2.get("TotalCpuTime");
        if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){
            cpuUsage = 1 - (float)(idleCpuTime2 - idleCpuTime1)/(float)(totalCpuTime2 - totalCpuTime1);
            //System.out.println("本节点CPU使用率为: " + cpuUsage);
        }

        return cpuUsage;
    }

    Map<String,Long> getCpuTime(SshHost sshHost,String command){
        Map<String,Long> time = new HashMap<String,Long>();
        String res= Commond.execute(sshHost,command).getContent();
        long idleCpuTime = 0, totalCpuTime = 0;	//分别为系统启动后空闲的CPU时间和总的CPU时间
        String[] resArray = res.split("\n");
        //System.out.println(resArray.length);
        for (String resLine:resArray) {
            if(resLine.startsWith("cpu")){
                resLine = resLine.trim();
                //System.out.println(resLine);
                String[] temp = resLine.split("\\s+");
                idleCpuTime = Long.parseLong(temp[4]);
                for(String s : temp){
                    if(!s.equals("cpu")){
                        totalCpuTime += Long.parseLong(s);
                    }
                }
                time.put("IdleCpuTime",idleCpuTime);
                time.put("TotalCpuTime",totalCpuTime);
                //System.out.println("IdleCpuTime: " + idleCpuTime + ", " + "TotalCpuTime：" + totalCpuTime);
                break;
            }
        }
        return time;
    }


}
