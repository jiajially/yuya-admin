package com.liug.webservice;

import com.liug.common.util.FileUtil;
import com.liug.common.util.MD5Util;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.controller.BaseController;
import com.liug.model.dto.LoginInfo;
import com.liug.model.dto.UserPermission;
import com.liug.model.entity.SysUser;
import com.liug.service.SysUserService;
import com.liug.service.SystemService;
import io.swagger.annotations.Api;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "用户管理模块")
@Controller
@RequestMapping("webservice/user")
public class WebserviceUserController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceUserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SystemService systemService;
    //用于存放用户登录后的唯一标识信息
    private static Map<String,SysUser> loginCodeMap = new HashMap<String,SysUser>();

    /**
     *  用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Result login(@RequestParam String loginName, @RequestParam String password, HttpServletRequest request) {
        //验证登录
        LoginInfo loginInfo = sysUserService.login(loginName,password);

        if (loginInfo!=null)
        {
            //success 返回md5值
            String loginCode = generateLoginCode(loginName,request);
            loginCodeMap.put(loginCode,sysUserService.selectByLoginName(loginName));
            //登录成功只返回loginCode
            return Result.success(loginCode);
        }
        else {
            return Result.instance(ResponseCode.password_incorrect.getCode(),ResponseCode.password_incorrect.getMsg());
        }

    }



    private String generateLoginCode(String loginName,HttpServletRequest request){
        //准备盐 用于生成loginCode
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();

        String host = request.getRemoteHost()==null?"none":request.getRemoteHost();
        String browser_id =""+browser.getId();
        String os_id = ""+os.getId();

        String factor = host.replace(".","").replace(":","") + browser_id + os_id;
        return MD5Util.generate(loginName+factor,MD5Util.generateSalt());
    }

    /**
     *  loginStatus
     *  验证是否登录，登录后可以获取用户信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loginStatus", method = RequestMethod.GET)
    public Result loginCodeExists(@RequestParam String loginCode) {

        //表示用户当前已登录
        if(loginCodeMap.containsKey(loginCode)){
            //获取用户基本信息
            SysUser currentUser = loginCodeMap.get(loginCode);
            return Result.success(currentUser);
        }else {
            //未登录
            return Result.instance(ResponseCode.unauthenticated.getCode(),ResponseCode.unauthenticated.getMsg());
        }


    }


    /**
     * 查询用户权限列表--测试
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "permissions", method = RequestMethod.GET)
    public List<UserPermission> list(@RequestParam String loginCode) {
        String userId = "000001";
        List<UserPermission> permissions = new ArrayList<UserPermission>();

        //权限1 访问系统权限
        UserPermission permission1 = new UserPermission();
        permission1.setUserId(userId);
        permission1.setName("system");
        permission1.setType("0");
        permission1.setPath("#");
        permission1.setValue("true");
        permissions.add(permission1);
        //权限2 访问系统权限
        UserPermission permission2 = new UserPermission();
        permission2.setUserId(userId);
        permissions.add(permission2);
        //权限3 访问系统权限
        UserPermission permission3 = new UserPermission();
        permission3.setUserId(userId);
        permissions.add(permission3);
        //权限4 访问系统权限
        UserPermission permission4 = new UserPermission();
        permission4.setUserId(userId);
        permissions.add(permission4);
        //权限5 访问系统权限
        UserPermission permission5 = new UserPermission();
        permission5.setUserId(userId);
        permissions.add(permission5);
        //权限6 访问系统权限
        UserPermission permission6 = new UserPermission();
        permission6.setUserId(userId);
        permissions.add(permission6);
        //权限7 访问系统权限
        UserPermission permission7 = new UserPermission();
        permission7.setUserId(userId);
        permissions.add(permission7);

        return permissions;
    }


    //用户图片上传
    @RequestMapping(value = "upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        // 文件上传后的路径，文件保存名为用户ID
        String filePath = FileUtil.getProjectPath()+"/file/picture/";
        if (file.isEmpty()) {
            return Result.instance(ResponseCode.file_not_exist.getCode(),ResponseCode.file_not_exist.getMsg());
        }else if(id==null){
            return Result.instance(ResponseCode.unknown_account.getCode(),ResponseCode.unknown_account.getMsg());
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        String suffix = "png";
        if (fileName.split(".").length==2) {
            suffix =fileName.split(".")[1];
            String destFileName =id + "." + suffix;
        }
        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);
        log.info(filePath);
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return Result.success();
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Result.error();
    }


    //用户图片下载
    @RequestMapping("/download")
    @ResponseBody
    public Result downloadMcode(HttpServletResponse response,HttpServletRequest request){
        try {
            String fileName = request.getParameter("fileName");
            String realPath = FileUtil.getProjectPath()+"/file/picture/";
            //下载机器码文件
            response.setHeader("conent-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));

            OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            InputStream is = null;

            is = new FileInputStream(realPath+fileName);
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
