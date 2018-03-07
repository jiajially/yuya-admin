package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.dao.*;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/8/12.
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
    @Autowired
    private ManagerProblemMapper managerProblemMapper;
    @Autowired
    private ManagerWorkMapper managerWorkMapper;
    @Autowired
    private SapScriptMapper sapScriptMapper;
    @Autowired
    private SapScriptStaticMapper sapScriptStaticMapper;
    @Autowired
    private SapSystemHostMapper sapSystemHostMapper;

    @Override
    public PageInfo selectProblemPage(int page, int rows, String sort, String order, Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = managerProblemMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<ManagerProblem> problems = managerProblemMapper.selectAll(sort, order, begin, end);
        PageInfo pageInfo = new PageInfo(counts, problems);
        return pageInfo;
    }

    @Override
    public long addProblem(String summary, String channel, String informer) {
        ManagerProblem managerProblem = new ManagerProblem();
        managerProblem.setChannel(channel);
        managerProblem.setSummary(summary);
        managerProblem.setInformer(informer);
        managerProblem.setDate(new Date(System.currentTimeMillis()));
        return managerProblemMapper.insert(managerProblem);
    }

    @Override
    public long dealProblem(ManagerProblem managerProblem) {
        return managerProblemMapper.update(managerProblem);
    }

    @Override
    public ManagerProblem getProblemById(long id) {
        return managerProblemMapper.selectById(id);
    }

    @Override
    public PageInfo selectWorkPage(int page, int rows, String sort, String order, Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = managerWorkMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<ManagerWork> works = managerWorkMapper.selectAll(sort, order, begin, end);
        PageInfo pageInfo = new PageInfo(counts, works);
        return pageInfo;
    }

    @Override
    public long addWork(String summary, String level, String type) {
        ManagerWork managerWork = new ManagerWork();
        managerWork.setSummary(summary);
        managerWork.setLevel(level);
        managerWork.setType(type);
        managerWork.setCreatedate(new Date(System.currentTimeMillis()));
        return managerWorkMapper.insert(managerWork);
    }

    @Override
    public long dealWork(ManagerWork managerWork) {
        return managerWorkMapper.update(managerWork);
    }

    @Override
    public long deleteWork(long id) {

        return managerWorkMapper.deleteById(id);
    }

    @Override
    public Result selectSapScript() {
        return Result.success(sapScriptMapper.selectAll());
    }

    @Override
    public Result selectSapScriptStatic() {
        return Result.success(sapScriptStaticMapper.selectAll());
    }

    @Override
    public Result generator(SapScript sapScript) {

        return generator(sapScript, "\\sap_script_test_v1\\");
    }
    @Override
    public Result download(InputStream is , OutputStream os){
        try {

            //OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            //InputStream is = new ByteArrayInputStream(content.getBytes("GB2312"));

            BufferedInputStream bis = new BufferedInputStream(is);


            int length = 0;
            byte[] temp = new byte[1 * 1024 * 10];

            while ((length = bis.read(temp)) != -1) {
                bos.write(temp, 0, length);
            }
            bos.flush();
            bis.close();
            bos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return Result.success();
        }
    }

    @Override
    public Result generator(SapScript sapScript, String path) {
        String content = "";
        content += "\n" + sapScriptStaticMapper.selectByCode("param").getContent();
        //设置参数
        /*
SYSTEM_SID 			= "EC6"
WORKSAPCE			= "\sap_script_demo5\"

USE_ROUTER      		= FALSE
HOST 				= "192.168.1.5"
INSTANCE_NO			= "00"
ROUTER_HOST			= "yuya-info.51vip.biz"
ROUTER_PORT			= "16547"

LOGIN_CLIENT			= "800"
LOGIN_USR			= "basis"
LOGIN_PWD			= "yuya0571"

         */

        content += "\n" + "SYSTEM_SID \t\t\t= \"" + sapScript.getSid() + "\"";
        content += "\n" + "WORKSAPCE\t\t\t= " + "\"" + path + "\"";
        content += "\n" + "USE_ROUTER \t\t\t= FALSE";
        if (sapScript.getRouter() != null) {
            content += "\n" + "USE_ROUTER \t\t\t= TRUE";
            content += "\n" + "ROUTER_HOST \t\t\t= \"" + sapScript.getRouter().split(":")[0] + "\"";
            content += "\n" + "ROUTER_PORT \t\t\t= \"" + sapScript.getRouter().split(":")[1] + "\"";
        }
        content += "\n" + "HOST \t\t\t= \"" + sapScript.getHost() + "\"";

        String instance_no_str = String.valueOf(sapScript.getInstanceno()).length() <= 1 ? "0" + sapScript.getInstanceno() : "" + sapScript.getInstanceno();


        content += "\n" + "INSTANCE_NO \t\t\t= \"" + instance_no_str + "\"";
        content += "\n" + "LOGIN_CLIENT \t\t\t= \"" + sapScript.getClient() + "\"";
        content += "\n" + "LOGIN_USR \t\t\t= \"" + sapScript.getUsername() + "\"";
        content += "\n" + "LOGIN_PWD \t\t\t= \"" + sapScript.getPassword() + "\"";


        content += "\n" + sapScriptStaticMapper.selectByCode("workspace").getContent();
        content += "\n" + sapScriptStaticMapper.selectByCode("gui").getContent();
        content += "\n" + sapScriptStaticMapper.selectByCode("word_create").getContent();
        //获取tcode
        if (sapScript.getTcode() != null) {
            String[] tcodes = sapScript.getTcode().split("\\*");
            for (String tcode : tcodes) {
                SapScriptStatic scriptStatic = sapScriptStaticMapper.selectByCode(tcode);
                if (scriptStatic != null) content += "\n" + scriptStatic.getContent();
            }
        }
        content += "\n" + "\n" + "CALL exec(\"\")";
        content += "\n" + sapScriptStaticMapper.selectByCode("word_end").getContent();
        content += "\n" + sapScriptStaticMapper.selectByCode("function").getContent();
        //logger.info(content              );
        //sapScriptMapper.insert(sapScript);
        return Result.success(content);
    }

    @Override
    public Result save(SapScript sapScript) {
        //logger.info(sapScript.toString());
        Result result = Result.instance(ResponseCode.error);
        try {

            result = Result.success(sapScriptMapper.insert(sapScript));
        } catch (Exception e) {
            result = Result.instance(ResponseCode.error);
        } finally {
            return result;
        }

    }

    @Override
    public Result addSapsystemHost(SapSystemHost sapSystemHost) {
        List<SapSystemHost> sapSystemHostList = sapSystemHostMapper.selectAll();
        for (SapSystemHost dbSapSystemHost:sapSystemHostList) {
            if(dbSapSystemHost.getHostId().equals(sapSystemHost.getHostId())&&dbSapSystemHost.getSapsystemId().equals(sapSystemHost.getSapsystemId())){
                return Result.error("该系统下的主机信息已存在");
            }
        }
        return Result.success(sapSystemHostMapper.insert(sapSystemHost));
    }
}
