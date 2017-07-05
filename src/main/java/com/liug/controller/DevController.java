package com.liug.controller;

import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.model.entity.CharRecg;
import com.liug.service.DevService;
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
    private DevService devService;

    @ApiOperation(value = "跳转至Dashboard", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dev/dashboard";
    }

    @ApiOperation(value = "跳转至Loadfile", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "loadfile", method = RequestMethod.GET)
    public String loadfile() {
        return "dev/loadfile";
    }


    /**
     * 读取服务器文件
     *
     * @param id          id
     * @param file        文件路径
     * @return
     */
    @ApiOperation(value = "读取服务器文件", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "cat", method = RequestMethod.GET)
    public Result queryScriptById(@RequestParam long id,@RequestParam String file) {
        CharRecg charRecg = devService.loadfile(id,file);
        if(charRecg.getCount()!=-1)return Result.success(charRecg);
        else return Result.instance(ResponseCode.error);
    }
}
