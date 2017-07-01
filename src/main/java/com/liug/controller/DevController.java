package com.liug.controller;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.common.util.StringUtil;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshHost;
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

import java.util.List;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "开发模块")
@Controller
@RequestMapping("dev")
public class DevController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(DevController.class);
    @Autowired
    private SshHostService sshHostService;

    @ApiOperation(value = "跳转至Dashboard", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String host() {
        return "dev/dashboard";
    }


}
