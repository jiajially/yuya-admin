package com.liug.webservice;

import com.jcraft.jsch.JSchException;
import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.Shell;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.*;
import com.liug.dao.*;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.model.json.Hardware;
import com.liug.model.json.Sid;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import com.liug.service.SshScriptService;
import com.liug.service.SshTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "管理模块")
@Controller
@RequestMapping("webservice/manager")
public class WebserviceMangerController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceMangerController.class);
    @Autowired
    private SshHostService sshHostService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SshScriptService sshScriptService;

    @Autowired
    private SshTaskService sshTaskService;

    @Autowired
    private SapScriptMapper sapScriptMapper;

    @Autowired
    private SapScriptStaticMapper sapScriptStaticMapper;


    @Autowired
    private CustomReportMapper customReportMapper;
    @Autowired
    private CustomReportResultMapper customReportResultMapper;
    @Autowired
    private CustomReportScriptMapper customReportSciptMapper;

    @Autowired
    private CustomReportScheduleMapper customReportScheduleMapper;


    /**
     * loginStatus
     * 获取主机信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "host", method = RequestMethod.GET)
    public Result loginCodeExists(@RequestParam String loginCode) {
        return Result.success(sshHostService.selectAllHost());
    }

    /**
     * 新增主机
     *
     * @param host     登录名
     * @param port     端口
     * @param username 主机用户
     * @param password 主机密码
     * @return
     */
    @ApiOperation(value = "新增主机", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/host/add", method = RequestMethod.POST)
    public Result insert(@RequestParam(required = true) String summary,
                         @RequestParam(required = true) String host,
                         @RequestParam(required = true) Integer port,
                         @RequestParam(required = true) String username,
                         @RequestParam(required = true) String password) {
        if (host != null && port != null && username != null && password != null && summary != null) {
            SshHost sshHost = new SshHost();
            sshHost.setHost(host);
            sshHost.setPort(port);
            sshHost.setUsername(username);
            sshHost.setPassword(password);
            sshHost.setSummary(summary);
            sshHost.setStatus(0);
            long id = sshHostService.insertHost(sshHost, new SshHostDetail());
            if (id == ResponseCode.host_already_exist.getCode())
                return Result.instance(ResponseCode.host_already_exist);
            else return Result.success(id);
        } else return Result.error("提交信息不完整！");
    }


    /**
     * 禁用/启用主机
     *
     * @param id       id
     * @param isEnable 启用状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "host/enable", method = RequestMethod.GET)
    public Result forbiddenHost(@RequestParam long id, @RequestParam boolean isEnable) {
        Result result;
        long temp = sshHostService.enableHost(id, isEnable);
        if (temp >= 0) result = Result.success(temp);
        else {
            result = Result.instance(ResponseCode.host_valid_enable_fail);
        }
        return result;
    }
    @RequestMapping(value = "/host/delete")
    @ResponseBody
    public Result deleteHost(@RequestParam long id) {
        return Result.success(sshHostService.deleteById(id));
    }

    /**
     * 验证主机有效性
     */
    @ResponseBody
    @RequestMapping(value = "host/prevalid", method = RequestMethod.GET)
    public Result validHost(
            @RequestParam String host,
            @RequestParam Integer port,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String summary) {
        Result result = null;
        if (host != null && port != null && username != null && password != null && summary != null) {
            SshResult sshResult;
            SshHost sshHost = new SshHost();
            sshHost.setId(-1l);
            sshHost.setHost(host);
            sshHost.setPort(port);
            sshHost.setPassword(password);
            sshHost.setUsername(username);
            sshHost.setSummary(summary);

            sshResult = sshHostService.validHost(sshHost);
            if (sshResult.getExitStatus() == 1) result = Result.success(sshResult);
            else if (sshResult.getExitStatus() == ResponseCode.host_already_exist.getCode())
                result = Result.instance(ResponseCode.host_already_exist);
            else result = Result.instance(ResponseCode.host_valid_fail.getCode(), sshResult.getContent());
        } else {
            result = Result.error("提交信息不完整！");
        }
        return result;
    }


    /**
     * 验证主机有效性
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "验证主机有效性", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "host/valid", method = RequestMethod.GET)
    public Result validHost(@RequestParam long id) {
        Result result;
        SshResult sshResult;
        SshHost sshHost = sshHostService.selectById(id);
        SshHostDetail sshHostDetail = new SshHostDetail(sshHost);
        sshResult = sshHostService.validHost(sshHost, sshHostDetail);
        if (sshResult.getExitStatus() == 1) result = Result.success(sshResult);
        else if (sshResult.getExitStatus() == ResponseCode.host_already_exist.getCode())
            result = Result.instance(ResponseCode.host_already_exist);
        else {
            //失败，刷新状态
            sshHost.setValid(false);
            sshHost.setEnable(false);
            sshHostService.updateHost(sshHost, sshHostDetail);
            result = Result.instance(ResponseCode.host_valid_fail.getCode(), sshResult.getContent());
        }
        return result;
    }


    /**
     * 查询指令脚本
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sshscript", method = RequestMethod.GET)
    public Result scriptList(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String hostname,
            @RequestParam(defaultValue = "", required = false) String cmd) {
        PageInfo pageInfo = sshScriptService.selectPage(1, 1000000, "id", "desc", name, hostname, cmd);
        return Result.success(pageInfo.getRows());
    }

    /**
     * 查询指令脚本执行记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sshscript/log", method = RequestMethod.GET)
    public Result scriptLogList(@RequestParam String scriptId) {
        Result result = sshTaskService.selectPage("id", "desc", scriptId);
        return result;
    }


    /**
     * 查询指令脚本
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sapscript", method = RequestMethod.GET)
    public Result scriptList() {
        return managerService.selectSapScript();
    }


    /**
     * 查询主机列表-选择框
     */
    @ResponseBody
    @RequestMapping(value = "hostselect", method = RequestMethod.GET)
    public Result select() {
        List<SelectContnet> selectContnets = sshHostService.select(true);
        return Result.success(selectContnets);
    }


    @RequestMapping("/sapscript/generator")
    @ResponseBody
    public Result downloadSapScript(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        //try {

        String content = "";
        String tcode = request.getParameter("tcode");
        String summary = request.getParameter("summary");
        String host = request.getParameter("host");
        String sid = request.getParameter("sid");
        String instanceNo = request.getParameter("instanceNo");
        String client = request.getParameter("client");
        String language = request.getParameter("language");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String router = request.getParameter("router");
        String connType = request.getParameter("connType");
        String timeout = request.getParameter("timeout");
        String title = request.getParameter("title");
        String formate = request.getParameter("formate");

        SapScript sapScript = new SapScript();
        sapScript.setTcode(tcode);
        sapScript.setClient(Integer.valueOf(client));
        sapScript.setFormate(formate);
        sapScript.setHost(host);
        sapScript.setInstanceno(Integer.valueOf(instanceNo));
        sapScript.setLanguage(language);
        sapScript.setPassword(password);
        sapScript.setRouter(router);
        sapScript.setSid(sid);
        sapScript.setSummary(summary);
        sapScript.setTimeout(Integer.valueOf(timeout));
        sapScript.setTitle(title);
        sapScript.setUsername(username);
        content = (String) managerService.generator(sapScript).getData();

        String fileName = "tmp" + System.currentTimeMillis() + ".vbs";
        //下载机器码文件
        response.setHeader("conent-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

        try {
            return managerService.download(new ByteArrayInputStream(content.getBytes("GB2312")), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }
            /*     OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            InputStream is = new ByteArrayInputStream(content.getBytes("GB2312"));

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
        }*/
    }

    @RequestMapping("/sapscript/generateById")
    @ResponseBody
    public Result downloadSapScriptByID(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {


        String id = request.getParameter("id");
        SapScript sapScript = sapScriptMapper.selectById(Long.valueOf(id));
        String content = (String) managerService.generator(sapScript).getData();

        //log.info(content);
        String fileName = "tmp" + System.currentTimeMillis() + ".vbs";
        //下载机器码文件
        response.setHeader("conent-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

        //return download(new ByteArrayInputStream(content.getBytes("GB2312")),response.getOutputStream());
        try {
            return managerService.download(new ByteArrayInputStream(content.getBytes("GB2312")), response.getOutputStream());
        } catch (IOException e) {
            return Result.error(e.getMessage());
        } finally {
            return Result.success();
        }

    }


    @RequestMapping("/sapscript/execById")
    @ResponseBody
    public Result execSapScriptByID(@RequestParam String id) {
        SapScript sapScript = sapScriptMapper.selectById(Long.valueOf(id));
        String content = (String) managerService.generator(sapScript, "\\file\\script\\vbs\\result\\").getData();
        String fileName = "tmp" + System.currentTimeMillis() + ".vbs";
        FileOutputStream fileOutputStream;
        File file = new File(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
        try {
            if (file.exists()) {
                file.createNewFile();
            }

            fileOutputStream = new FileOutputStream(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
            fileOutputStream.write(content.getBytes("GB2312"));
            fileOutputStream.flush();
            fileOutputStream.close();

            //执行
            VBAUtil.execVBS(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }
    }


    @RequestMapping(value = "/sapscript/save", method = RequestMethod.POST)
    @ResponseBody
    public Result downloadMcode(
            @RequestParam String tcode,
            @RequestParam String summary,
            @RequestParam String host,
            @RequestParam String sid,
            @RequestParam String instanceNo,
            @RequestParam String client,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String router,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String timeout,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String formate) {

        SapScript sapScript = new SapScript();
        sapScript.setTcode(tcode);
        sapScript.setClient(Integer.valueOf(client));
        sapScript.setFormate(formate);
        sapScript.setHost(host);
        sapScript.setInstanceno(Integer.valueOf(instanceNo));
        sapScript.setLanguage(language);
        sapScript.setPassword(password);
        sapScript.setRouter(router);
        sapScript.setSid(sid);
        sapScript.setSummary(summary);
        sapScript.setTimeout(Integer.valueOf(timeout));
        sapScript.setTitle(title);
        sapScript.setUsername(username);

        return managerService.save(sapScript);

    }


    /**
     * 新增指令
     *
     * @param name      登录名
     * @param host      主机用户
     * @param cmd       主机密码
     * @param startTime 指令
     * @param endTime   备注
     * @param rate      执行结果
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sshscript/add", method = RequestMethod.POST)
    public Result insert(@RequestParam(required = true) String name,
                         @RequestParam(required = true) long host,
                         @RequestParam(required = true) String cmd,
                         @RequestParam(defaultValue = "", required = false) String startTime,
                         @RequestParam(defaultValue = "", required = false) String endTime,
                         @RequestParam(defaultValue = "1", required = false) Integer rate) {
        SshScript sshScript = new SshScript();
        sshScript.setName(name);
        sshScript.setHostId(host);
        sshScript.setCmd(cmd);
        sshScript.setStartTime(StringUtil.string2Date(startTime));
        sshScript.setEndTime(StringUtil.string2Date(endTime));

        sshScript.setRate(rate);
        long id = sshScriptService.insertScript(sshScript);
        if (id == -1) return Result.instance(ResponseCode.code_already_exist);
        else return Result.success(id);
    }


    /**
     * 初始化
     *
     * @return
     */
    @ApiOperation(value = "初始化", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public Result initDir() {
        FileUtil.initDictionary("/file");
        return Result.success();
    }

    /**
     * sap result fileList
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sapscript/filelist", method = RequestMethod.GET)
    public Result sapFileList() {
        List<FileStruct> fileStructList = null;
        try {
            fileStructList = FileUtil.readfileList(FileUtil.getProjectPath() + "/file/script/vbs/result");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(fileStructList);
    }

    @RequestMapping(value = "/sapscript/file/download", method = RequestMethod.GET)
    public Result getFile(HttpServletResponse resp, @RequestParam("filename") String filename) throws UnsupportedEncodingException {

        String path = FileUtil.getProjectPath() + "/file/script/vbs/result/" + filename;
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(filename, "utf-8"));
        try {
            return managerService.download(new BufferedInputStream(new FileInputStream(file)), resp.getOutputStream());
            //FileUtil.fileDownloader(new BufferedInputStream(new FileInputStream(file)), resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }
/*

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            os.close();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Result.success();
        }*/
    }

    @RequestMapping(value = "/sshscript/log/download", method = RequestMethod.GET)
    @ResponseBody
    public Result downloadSshScriptLogByID(HttpServletResponse response, @RequestParam("id") Long id) throws UnsupportedEncodingException {
       /* try {

            String content =sshTaskService.selectById(id).getResult();
            String fileName = "tmp" + System.currentTimeMillis() + ".log";
            //下载机器码文件
            response.setHeader("conent-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

            OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));

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
        }*/

        String content = sshTaskService.selectById(id).getResult();
        String fileName = "tmp" + System.currentTimeMillis() + ".log";
        //下载机器码文件
        response.setHeader("conent-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));
        try {
            return managerService.download(new ByteArrayInputStream(content.getBytes("UTF-8")), response.getOutputStream());
            //FileUtil.fileDownloader(new BufferedInputStream(new ByteArrayInputStream(content.getBytes("UTF-8"))), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }

    }

    @ResponseBody
    @RequestMapping(value = "/sapscript/sync")
    public Result sapscriptSync(@RequestParam String dest) {
        //List<SapScriptStatic> list = new ArrayList<SapScriptStatic>();
        String root_path = FileUtil.getProjectPath() + "/file/script/vbs/tcode/";
        if (dest.equals("file")) {
            List<SapScriptStatic> sapScriptStaticList = sapScriptStaticMapper.selectAll();
            for (SapScriptStatic scriptStatic : sapScriptStaticList) {
                String path = root_path + scriptStatic.getType() + "/";
                File dictionary = new File(path);
                if (!dictionary.exists()) dictionary.mkdirs();
                FileUtil.createFileForce(path + scriptStatic.getCode(), scriptStatic.getContent());
            }
        } else if (dest.equals("db")) {
            File root = new File(root_path);
            File[] dictionarys = root.listFiles();
            for (File dictionary : dictionarys) {
                if (dictionary.isDirectory()) {
                    String type = dictionary.getName();
                    File[] tcode_files = dictionary.listFiles();
                    for (File tcode : tcode_files) {
                        SapScriptStatic sapScriptStatic = sapScriptStaticMapper.selectByCode(tcode.getName());
                        String content = FileUtil.loadFile(tcode.getAbsolutePath(), "UTF-8");
                        if (content == null || content.trim() == "") content = "\n";
                        if (sapScriptStatic != null) {
                            sapScriptStatic.setContent(content);
                            sapScriptStaticMapper.update(sapScriptStatic);
                        } else {
                            sapScriptStatic = new SapScriptStatic();
                            sapScriptStatic.setContent(content);
                            sapScriptStatic.setType(type);
                            sapScriptStatic.setCode(tcode.getName());
                            sapScriptStatic.setSummary(tcode.getName());
                            sapScriptStaticMapper.insert(sapScriptStatic);
                        }

                        //list.add(sapScriptStatic);
                    }

                }

            }
        }

        return Result.success();
    }


    @ResponseBody
    @RequestMapping(value = "/customreport")
    public Result customreportList(@RequestParam String group) {
        return Result.success(customReportMapper.selectAll());
    }
    @ResponseBody
    @RequestMapping(value = "/customreport/schedule")
    public Result customreportList(@RequestParam Long reportId) {
        return Result.success(customReportScheduleMapper.selectByReportId(reportId));
    }

    @ResponseBody
    @RequestMapping(value = "/customreport/schedule/BySid")
    public Result customreportListBySid(@RequestParam Long sidId) {
        return Result.success(customReportScheduleMapper.selectBySid(sidId));
    }

    @ResponseBody
    @RequestMapping(value = "/customreport/schedule/delete")
    public Result deleteCustomreport(@RequestParam Long id) {
        return Result.success(customReportScheduleMapper.deleteById(id));
    }
    @ResponseBody
    @RequestMapping(value = "/customreport/result/ByScheduleId")
    public Result customreportScheduleId(@RequestParam Long scheduleId,@RequestParam boolean isRate) {
        List<CustomReportResult> list = customReportResultMapper.selectByScheduleId(scheduleId);
        if (!isRate&&list.size() == 1) return Result.success(list.get(0).getId());
        else if(isRate&&list.size() >0) return Result.success(list);
        else return Result.error();
    }


    @ResponseBody
    @RequestMapping(value = "/customreport/result")
    public Result customreportResult(@RequestParam Long id) {
        CustomReportResult reportResult = customReportResultMapper.selectById(id);
        if(reportResult.getStatus()==1)return Result.success(JSONArray.fromObject(reportResult.getResultJson()));
        else if(reportResult.getStatus()==3)return Result.error("未执行完成");
        else return Result.error("执行失败");
    }

    @ResponseBody
    @RequestMapping(value = "/customreport/script")
    public Result customreportScript() {
        return Result.success(customReportSciptMapper.selectAll());
    }

    @ResponseBody
    @RequestMapping(value = "/customreport/script/delete")
    public Result customreportScript(@RequestParam Long id) {
        return Result.success(customReportSciptMapper.deleteById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/customreport/schedule/add")
    public Result customreportScript(@RequestParam Long hostId,
                                     @RequestParam Long reportId,
                                     @RequestParam(required = false) Date starttime,
                                     @RequestParam(required = false)Integer rate) {
        CustomReportSchedule schedule  =new CustomReportSchedule();
        schedule.setRate(-1);
        schedule.setHost(hostId);
        schedule.setReport(reportId);
        schedule.setStartTime(starttime);

        if (rate!=null&&rate > 0) {
            String rate_str =String.valueOf(rate);
            int cycle=GregorianCalendar.SECOND;
            int count=Integer.valueOf(rate_str.substring(1,rate_str.length()));
            switch(rate_str.substring(0,1)){
                case "1":cycle=GregorianCalendar.DATE;break;
                case "2":cycle=GregorianCalendar.WEDNESDAY;break;
                case "3":cycle=GregorianCalendar.MONTH;break;
                default:cycle=GregorianCalendar.SECOND;break;
            }
            GregorianCalendar planTime =new GregorianCalendar();
            planTime.setTime(starttime);
            while(planTime.getTime().before(new GregorianCalendar().getTime())){
                planTime.add(cycle, count);
            }
            schedule.setPlanTime(planTime.getTime());
            schedule.setRate(rate);
        }
        return Result.success(customReportScheduleMapper.insert(schedule));
    }
    @ResponseBody
    @RequestMapping(value = "/customreport/script/add")
    public Result customreportScript(@RequestParam  String title,
                                     @RequestParam Long reportId,
                                     @RequestParam Integer fomattor,
                                     @RequestParam Integer orderno,
                                     @RequestParam Integer type,
                                     @RequestParam String cmd) {
        CustomReportScript script  =new CustomReportScript();
        script.setOrderno(orderno);
        script.setTitle(title);
        script.setType(type);
        script.setReportId(reportId);
        script.setScriptContent(cmd);
        String fomatter_str="{}";
        JSONObject object = JSONObject.fromObject(fomatter_str);
        switch(fomattor){
            //case 10:object.put("isTable",true);break;
            case 11:object.put("isTable",true);object.put("fistLineSpaceCount",1);break;
            case 12:object.put("isTable",true);object.put("fistLineSpaceCount",0);break;
            default:object.put("isTable",false);
        }
        script.setFomattor(object.toString());
        return Result.success(customReportSciptMapper.insert(script));
    }





}
