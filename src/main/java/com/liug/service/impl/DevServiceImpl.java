package com.liug.service.impl;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SshChartResult;
import com.liug.common.ssh.SshResult;
import com.liug.dao.MonitorJobMapper;
import com.liug.dao.MonitorLogMapper;
import com.liug.dao.SshTaskMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.service.DevService;
import com.liug.service.SshHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/7/2.
 */
@Service
@Transactional
public class DevServiceImpl implements DevService {
    @Autowired
    SshHostService sshHostService;
    @Autowired
    MonitorJobMapper monitorJobMapper;
    @Autowired
    MonitorLogMapper monitorLogMapper;
    @Autowired
    SshTaskMapper sshTaskMapper;

    @Override
    public CharRecg loadfile(long id, String path) {
        CharRecg charRecg = new CharRecg();
        charRecg.setCharacter("CREATE");
        charRecg.setCount(-1);
        //读取文件指令
        String cmd = "cat ";
        cmd += path;
        SshResult sshResult;
        SshHost sshHost = sshHostService.selectById(id);
        if(sshHost!=null){
            sshResult = Commond.execute(sshHost,cmd);//
            //文字识别变色
            String content= sshResult.getContent();
            //获取字符数量
            int counter=0;
            if (content.indexOf(charRecg.getCharacter()) == -1) {
                counter = 0;
            }
            while(content.indexOf(charRecg.getCharacter())!=-1){
                counter++;
                content=content.substring(content.indexOf(charRecg.getCharacter())+charRecg.getCharacter().length());
            }
            charRecg.setContent( sshResult.getContent().replace(charRecg.getCharacter(),"<font color=\"#c24f4a\">"+charRecg.getCharacter()+"</font>"));
            charRecg.setCount(counter);
        }
        return charRecg;
    }

    @Override
    public List<MonitorLog> selectMonitorLog(long hostId, Integer type) {
        List<MonitorJob> monitorJobs = monitorJobMapper.selectJob(hostId,type);
        if (monitorJobs.size()==1){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,-1);
            return monitorLogMapper.selectByJobId(monitorJobs.get(0).getId(),c.getTime());
        }else {
            return null;
        }
    }

    @Override
    public List<SshChartResult> getTaskLogSum(Long hostId){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        //c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),0,0);

        List<Date> dateList = new ArrayList<Date>();
        for(int i=0;i<24;i++) {
            dateList.add(c.getTime());
            c.add(Calendar.HOUR_OF_DAY,-1);
            int hour = c.get(Calendar.HOUR_OF_DAY);
        }
        List<SshTaskLogSum> sshTaskLogSums = sshTaskMapper.selectLogSumByHostID(hostId);
        //补全
        List<SshChartResult> sshChartResults = new ArrayList<SshChartResult>();
        for (Date date1: dateList){
            String dateRef = formatter.format(date1).toString().trim();
            SshChartResult sshChartResult = new SshChartResult();
            sshChartResult.setDateRef(dateRef);
            for (SshTaskLogSum sshTaskLogSum:sshTaskLogSums) {
                int failCount =0,successCount=0;
                if (sshTaskLogSum.getDateRef().equals(dateRef)){
                    if (sshTaskLogSum.getStatus()==1) sshChartResult.setSuccessCount(sshTaskLogSum.getCnt());
                    if (sshTaskLogSum.getStatus()==2) sshChartResult.setFailCount(sshTaskLogSum.getCnt());
                }
            }
            sshChartResults.add(sshChartResult);
        }

        return sshChartResults;
    }

    @Override
    public String monitor(Long hostId,int type) {
        if(type==1){

        }else if (type==2){

        }
        return null;
    }
}
