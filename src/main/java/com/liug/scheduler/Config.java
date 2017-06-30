package com.liug.scheduler;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SshResult;
import com.liug.dao.SshTaskMapper;
import com.liug.model.entity.SshScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by liugang on 2017/6/22.
 * 定时任务配置
 */

@Configuration
@EnableScheduling // 启用定时任务
public class Config {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SshTaskMapper sshTaskMapper;

    @Scheduled(cron = "* * * * * ?") // 每秒执行一次
    public void scheduler() {
        //1.获取列表
        int _tmpCount = sshTaskMapper.selectSshTaskCounts();
        if (_tmpCount>0) logger.info(">>>>>>>>>>>>> scheduled ... count:" + _tmpCount);
        List<SshScript> sshScripts = sshTaskMapper.selectSshTask("id","asc");
        for (SshScript sshScript:sshScripts) {
            scriptExec(sshScript);
        }
    }

    void scriptExec(SshScript sshScript){
        SshResult sshResult= Commond.execute(sshScript.getSshHost(),sshScript.getCmd());
        int _tmpStatus = 2;
        if (sshResult.getExitStatus()==0)_tmpStatus = 1;
        sshTaskMapper.updateSshResult(sshScript.getTaskId(),_tmpStatus,sshResult.getContent());

    }
}
