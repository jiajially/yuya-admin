package com.liug.webservice;

import com.liug.common.util.FileUtil;
import com.liug.common.util.MD5Util;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.model.dto.LoginInfo;
import com.liug.model.dto.UserPermission;
import com.liug.model.entity.HomePage;
import com.liug.model.entity.SysUser;
import com.liug.service.DevService;
import com.liug.service.SysUserService;
import com.liug.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "用户管理模块")
@Controller
@RequestMapping("webservice")
public class WebserviceController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceController.class);

    //用于存放用户登录后的唯一标识信息
    private static Map<String,SysUser> loginCodeMap = new HashMap<String,SysUser>();
    @Autowired
    DevService devService;


    /**
     * 获取customscript
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customscript", method = RequestMethod.GET)
    public Result select() {
        return Result.success(devService.getHomePage());
    }


    /**
     * 新增customscript
     *
     * @param host          主机
     * @param title         标题
     * @param description   描述
     * @param unit          单位
     * @param cmd           指令
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customscript/add", method = RequestMethod.POST)
    public Result insert(@RequestParam(required = true)Long host,
                         @RequestParam(required = true)String title,
                         @RequestParam(required = false)String description,
                         @RequestParam(required = false)String unit,
                         @RequestParam(required = true)String cmd) {
        HomePage homePage = new HomePage();
        homePage.setCmd(cmd);
        homePage.setDescription(description);
        homePage.setHomepage("0");
        homePage.setHostId(host);
        homePage.setTitle(title);
        homePage.setUnit(unit);
        homePage.setStatus(1);
        return devService.addHomePage(homePage);
    }


    /**
     * customscript toolbox
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customscript/toolbox", method = RequestMethod.GET)
    public Result toolbox(@RequestParam(required = true)Long id,
                          @RequestParam(required = true)Integer type) {
        //   return null;
        return Result.success(devService.toolbox(id,type));
    }



}
