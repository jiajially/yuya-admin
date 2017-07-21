package com.liug.scheduler;

import com.liug.common.ssh.Commond;
import com.liug.dao.MonitorJobMapper;
import com.liug.dao.MonitorLogMapper;
import com.liug.dao.SshHostMapper;
import com.liug.model.entity.MonitorJob;
import com.liug.model.entity.MonitorLog;
import com.liug.model.entity.SshHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liugang on 2017/7/10.
 * 内存监控
 * 一分钟1次
 */
@Component
public class MemScheduleJob extends ScheduleJob {

    private static final Logger logger = LoggerFactory.getLogger(MemScheduleJob.class);

    @Autowired
    SshHostMapper sshHostMapper;

    @Autowired
    MonitorLogMapper monitorLogMapper;

    @Async
    void doJob(Long hostId,Long jobId){
        long start = System.currentTimeMillis();
        logger.debug("内存监控开始>>>>>hostId:"+hostId+">>>>jobId:"+jobId);
        //Step1 获取主机信息
        SshHost sshHost = sshHostMapper.selectById(hostId);
        //Step2 透过主机信息获取内存数据
        //内存占比获取脚本如下
        //free  | sed -n '2p' | awk '{print $3/$2}'
        String result = String.valueOf(this.getMemPercent(sshHost,"free  | sed -n '2p' | awk '{print $3/$2}'"));
        MonitorLog monitorLog = new MonitorLog();
        monitorLog.setHostId(hostId);
        monitorLog.setJobId(jobId);
        monitorLog.setResult(result);
        monitorLogMapper.insert(monitorLog);
        long end = System.currentTimeMillis();
        logger.debug("内存监控监控>>>>>hostId:"+hostId+">>>>jobId:"+jobId+">>>>耗时："+(end - start)+"ms");
    }


}
