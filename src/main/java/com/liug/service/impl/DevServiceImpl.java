package com.liug.service.impl;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshChartResult;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.Result;
import com.liug.dao.*;
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
    @Autowired
    HomePageMapper homePageMapper;

    //@Override
    public CharRecg loadfile1(long id, String path,String word) {
        CharRecg charRecg = new CharRecg();
        charRecg.setCharacter(word);
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

            charRecg.setContent( sshResult.getContent().replace(charRecg.getCharacter().trim(),"<font color=\"#FF0000\">"+charRecg.getCharacter()+"</font>"));
            charRecg.setCount(counter);
        }
        return charRecg;
    }

    @Override
    public CharRecg loadfile(long id, String path,String word) {
        CharRecg charRecg = new CharRecg();
        charRecg.setCharacter(word);
        charRecg.setCount(-1);
        //读取文件指令
        String cmd = "cat ";
        cmd += path;
        SshResult sshResult;
        SshHost sshHost = sshHostService.selectById(id);
        if(sshHost!=null){
            sshResult = Commond.execute(sshHost,cmd);//
            //文字识别变色
            String content= "";
            //获取字符数量
            int counter=0;
            int index_line=0;


            String [] contentLines = sshResult.getContent().split("\n");

            for (String contentLine:contentLines) {
                index_line++;
                String lineResult = contentLine.replace(charRecg.getCharacter().trim(),"<font color=\"#FF0000\">"+charRecg.getCharacter()+"</font>");
                String prefix=index_line+">>>  ";
                int lineCounter = 0;

                if (contentLine.indexOf(charRecg.getCharacter()) == -1) {
                    lineCounter = 0;
                }else{prefix = "<font color=\"#FF0000\">"+prefix+"</font>";}
                while(contentLine.indexOf(charRecg.getCharacter())!=-1){
                    counter++;
                    lineCounter++;
                    contentLine=contentLine.substring(contentLine.indexOf(charRecg.getCharacter())+charRecg.getCharacter().length());
                }

                content += prefix + lineResult+"\n";
                
            }

            charRecg.setContent( content);
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
        //启用
        if(type==1){
            monitorJobMapper.deleteByHostId(hostId);
            MonitorJob cpuJob = new MonitorJob(11);
            MonitorJob memJob = new MonitorJob(12);
            cpuJob.setHostId(hostId); memJob.setHostId(hostId);
            cpuJob.setRate(60);memJob.setRate(60);
            monitorJobMapper.insert(cpuJob);
            monitorJobMapper.insert(memJob);
        }
        //停止
        else if (type==2){
            monitorJobMapper.deleteByHostId(hostId);
        }
        return "success";
    }

    @Override
    public Result addHomePage(HomePage homePage) {
        //首先执行一遍
        SshHost sshHost  = sshHostService.selectById(homePage.getHostId());
        SshResult sshResult= Commond.execute(sshHost,homePage.getCmd());
        homePage.setResult(sshResult.getContent());
        homePage.setUpdateTime(new Date(System.currentTimeMillis()));
        //执行插入
        return Result.success(homePageMapper.insert(homePage));
    }
    @Override
    public List<HomePage> getHomePage(){
        return  homePageMapper.selectAll("id","asc",null);
    }

    @Override
    public Result toolbox(long id, int type) {
        Result result=null;
        HomePage homePage = homePageMapper.selectById(id);
        //首先执行一遍
        SshHost sshHost  = sshHostService.selectById(homePage.getHostId());
        SshResult sshResult= Commond.execute(sshHost,homePage.getCmd());
        homePage.setResult(sshResult.getContent());
        homePage.setUpdateTime(new Date(System.currentTimeMillis()));
        //执行插入
        switch (type){
            case 1:result = Result.success(homePageMapper.deleteById(id));break;
            case 2:result = Result.success(homePageMapper.update(homePage));break;
            default:break;
        }
        return result;
    }
    @Override
    public Result flash(){
        List<HomePage> homePages = homePageMapper.selectAll("id","asc",null);
        for (HomePage homePage: homePages) {
            SshHost sshHost  = sshHostService.selectById(homePage.getHostId());
            SshResult sshResult= Commond.execute(sshHost,homePage.getCmd());
            homePage.setResult(sshResult.getContent());
            homePage.setUpdateTime(new Date(System.currentTimeMillis()));
            homePageMapper.update(homePage);
        }
        return Result.success();
    }

}
