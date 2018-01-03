package com.liug.webservice;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.*;
import com.liug.dao.SapScriptMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SapScript;
import com.liug.model.entity.SshHost;
import com.liug.model.entity.SshHostDetail;
import com.liug.model.entity.SshScript;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import com.liug.service.SshScriptService;
import com.liug.service.SshTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        PageInfo pageInfo = sshScriptService.selectPage(1, 1000000, "host", "asc", name, hostname, cmd);
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
        Result result = sshTaskService.selectPage("id", "asc", scriptId);
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
        List<SelectContnet> selectContnets = sshHostService.select();
        return Result.success(selectContnets);
    }


    @RequestMapping("/sapscript/generator")
    @ResponseBody
    public Result downloadSapScript(HttpServletResponse response, HttpServletRequest request) {
        try {

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

            OutputStream os = response.getOutputStream();
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
        }
    }

    @RequestMapping("/sapscript/generateById")
    @ResponseBody
    public Result downloadSapScriptByID(HttpServletResponse response, HttpServletRequest request) {
        try {

            String id = request.getParameter("id");

            SapScript sapScript = sapScriptMapper.selectById(Long.valueOf(id));

            String content = (String) managerService.generator(sapScript).getData();

            String fileName = "tmp" + System.currentTimeMillis() + ".vbs";
            //下载机器码文件
            response.setHeader("conent-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

            OutputStream os = response.getOutputStream();
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
        }
    }


    @RequestMapping("/sapscript/execById")
    @ResponseBody
    public Result execSapScriptByID(@RequestParam String id) {
        SapScript sapScript = sapScriptMapper.selectById(Long.valueOf(id));
        String content = (String) managerService.generator(sapScript,"\\file\\script\\vbs\\result\\").getData();
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
        }finally {
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
        try {
            sshScript.setStartTime(StringUtil.string2Date(startTime));
            sshScript.setEndTime(StringUtil.string2Date(endTime));
        } catch (Exception e) {
        }
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
            fileStructList = FileUtil.readfileList(FileUtil.getProjectPath()+"/file/script/vbs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(fileStructList);
    }

    @RequestMapping(value = "/sapscript/file/download", method = RequestMethod.GET)
    public Result getFile(HttpServletResponse resp, @RequestParam("filename")String filename) throws UnsupportedEncodingException {

        String path = FileUtil.getProjectPath() + "//file/script/vbs/" + filename;
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(filename,"utf-8"));
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
        }
    }

    @RequestMapping(value = "/sshscript/log/download", method = RequestMethod.GET)
    @ResponseBody
    public Result downloadSshScriptLogByID(HttpServletResponse response, @RequestParam("id")Long id) {
        try {


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
        }
    }
}
