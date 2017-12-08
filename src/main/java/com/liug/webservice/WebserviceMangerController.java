package com.liug.webservice;

import com.liug.common.ssh.SshResult;
import com.liug.common.util.FileUtil;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.model.entity.SapScript;
import com.liug.model.entity.SshHost;
import com.liug.model.entity.SshHostDetail;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
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
            sshHostService.updateHost(sshHost,sshHostDetail);
            result = Result.instance(ResponseCode.host_valid_fail.getCode(), sshResult.getContent());
        }
        return result;
    }


    //用户图片下载
    @RequestMapping("/sapscript/generator")
    @ResponseBody
    public void downloadMcode(HttpServletResponse response, HttpServletRequest request){
        try {

            String content="";
            String tcode = request.getParameter("tcode");
            SapScript sapScript = new SapScript();
            sapScript.setTcode(tcode);
            content = (String)managerService.generator(sapScript).getData();

            String fileName = "tmp"+System.currentTimeMillis()+".vbs";
            //下载机器码文件
            response.setHeader("conent-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

            OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            InputStream is = new ByteArrayInputStream(content.getBytes());

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
        }
    }


}
