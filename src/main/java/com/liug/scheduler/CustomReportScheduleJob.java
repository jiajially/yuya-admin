package com.liug.scheduler;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.FileUtil;
import com.liug.dao.*;
import com.liug.model.entity.*;
import com.liug.model.json.CustomReportItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liugang on 2017/7/10.
 * 自定义报表处理任务
 * 一分钟1次
 */
@Component
public class CustomReportScheduleJob {

    private static final Logger logger = LoggerFactory.getLogger(CustomReportScheduleJob.class);

    @Autowired
    SshHostMapper sshHostMapper;

    @Autowired
    CustomReportMapper customReportMapper;

    @Autowired
    CustomReportScriptMapper customReportScriptMapper;

    @Autowired
    CustomReportScheduleMapper customReportScheduleMapper;

    @Autowired
    CustomReportResultMapper customReportResultMapper;

    static List<CustomReportResult> customReportResultList = new ArrayList<CustomReportResult>();

    @Async
    public void initFileData() throws ParseException {
        long start = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse("2005-01-01");
        Date end = sdf.parse("2017-09-01");
        SshHost sshHost = new SshHost();
        while (begin.before(end)) {
            logger.info("现在处理:" + sdf.format(begin));
            initCheckResult(sshHost, begin);
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            begin = c.getTime();

        }
        long stop = System.currentTimeMillis();
        logger.debug("例行检查>>>>>耗时：" + (stop - start) + "ms");
    }

    /**
     * 例行检查执行框架
     */
    @Async
    void doJob() {
        long start = System.currentTimeMillis();
        logger.debug(String.valueOf("size:" + customReportResultList.size()));

        List<CustomReportResult> processList = customReportResultMapper.selectProcessList();
        for (CustomReportResult process : processList) {

            logger.debug(String.valueOf(customReportResultList.contains(process)));
            if (!customReportResultList.contains(process)) customReportResultList.add(process);
        }

        for (CustomReportResult result : customReportResultList) {
            checkResult(result);
        }
        //checkResult(sshHost);

        long stop = System.currentTimeMillis();
        //logger.debug("例行检查>>>>>耗时："+(stop - start)+"ms");
    }


    /**
     * Purpose:主操作
     */
    public void checkResult(CustomReportResult result) {
        int old_status = result.getStatus();
        if (old_status == 3) {
            result.setStatus(2);
            //获取host
            SshHost host = sshHostMapper.selectById(result.getHost());
            //操作1、查询执行脚本
            List<CustomReportScript> scripts = customReportScriptMapper.selectByReportId(result.getReport());
            List<CustomReportItem> items = new ArrayList<CustomReportItem>();
            for (CustomReportScript script : scripts) {
                CustomReportItem item = new CustomReportItem();
                item.setFomattor(script.getFomattor());
                item.setOrderno(script.getOrderno());
                item.setScriptContent(script.getScriptContent());
                item.setTitle(script.getTitle());

                getResult(item,host);
                //根据上面的配置执行得出结果
                //item.setResult("");

                items.add(item);
            }
            result.setStatus(1);
            result.setResultJson(JSONArray.fromObject(items).toString());
            //将result保存至数据库
            customReportResultMapper.update(result);
            CustomReportSchedule schedule = customReportScheduleMapper.selectById(result.getReport());
            if (schedule.getRate() > 0) {
                String rate_str =String.valueOf(schedule.getRate());
                int cycle=GregorianCalendar.SECOND;
                int count=Integer.valueOf(rate_str.substring(1,rate_str.length()));
                switch(rate_str.substring(0,1)){
                    case "1":cycle=GregorianCalendar.DATE;break;
                    case "2":cycle=GregorianCalendar.WEDNESDAY;break;
                    case "3":cycle=GregorianCalendar.MONTH;break;
                    default:cycle=GregorianCalendar.SECOND;break;
                }
                GregorianCalendar planTime = new GregorianCalendar();
                planTime.setTime(schedule.getStartTime());
                planTime.add(cycle, schedule.getRate());
                schedule.setPlanTime(planTime.getTime());
                customReportScheduleMapper.update(schedule);
            }
            logger.debug("操作执行>>>" + result.getId());
        }
    }

    /*
    *执行结果
    * */
    private boolean getResult(CustomReportItem item,SshHost host){
        String result=null;

        SshResult sshResult = Commond.execute(host,item.getScriptContent());
        result = sshResult.getContent();
        try {
            Object o = getFomateData(item.getFomattor(), "fistLineSpaceCount");
            if (o != null && ((int) o) >= 0) result = resultFormate(result, (int) o);
        }catch(Exception e){}
        if(result==null||result.trim()=="")result="执行失败";
        item.setResult(result);
        logger.debug("执行了"+sshResult.toString());
        return true;
    }


    //针对df -h 代码首行处理
    private String resultFormate(String result,int fistLineSpaceCount){

        List<List<String>> list = new ArrayList<>();
        String[] arry = result.split("\n");
        int maxColumn = 0;
        for (int i = 0; i < arry.length; i++) {
            String[] t_split = arry[i].split(" ");
            int tmpMaxColumn = 0;

            List<String> listItem = new ArrayList<String>();
            while (i == 0 & fistLineSpaceCount > 0) {
                listItem.add("");
                fistLineSpaceCount--;
                tmpMaxColumn++;
            }
            for (String item : t_split) {
                if (!item.trim().equals("")) {
                    listItem.add(item);
                    tmpMaxColumn++;
                }
            }
            maxColumn = tmpMaxColumn > maxColumn ? tmpMaxColumn : maxColumn;
            list.add(listItem);
        }

        for (List<String> item : list) {
            int tmpNeedSpace = maxColumn - item.size();
            while (tmpNeedSpace > 0) {
                item.add("");
                tmpNeedSpace--;
            }
        }

        return JSONArray.fromObject(list).toString();
    }

    private Object getFomateData(String jsonSourceStr,String key){
        JSONObject object = JSONObject.fromObject(jsonSourceStr);
        return object.containsKey(key)?object.get(key):null;
    }


    /**
     * Purpose:例行检查结果历史数据初始化
     */
    public void initCheckResult(SshHost sshHost, Date exectime) {
        /*String result=null;

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
        }*/
    }

    //执行例行检查,目前接口未知，模拟出结果
    private String doCheck(SshHost sshHost, String type, String exectime) {
        String path = FileUtil.getProjectPath() + "/file/" + type + "/" + type + exectime + ".htm";
        String tmp = null;
        try {
            File file = new File(path);
            logger.info(path);
            if (file.exists()) {
                InputStream is = new FileInputStream(new File(path));
                tmp = IOUtils.toString(is, "GB2312");
            } else tmp = null;
        } catch (IOException e) {

        }
        return tmp;
    }

}
