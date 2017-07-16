package com.liug.controller;

import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.dao.SshTaskMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.CharRecg;
import com.liug.model.entity.MonitorLog;
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
    private DevService devService;

    @Autowired
    private SshTaskMapper sshTaskMapper;

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

    /**
     * 获取主机监控信息
     *
     * @param hostId    主机ID
     * @return
     */
    @ApiOperation(value = "查询监控明细", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/monitor/log", method = RequestMethod.GET)
    public Result list(@RequestParam(required = true) Long hostId,
                         @RequestParam(required = true) Integer type) {
        List<MonitorLog> monitorLogs = devService.selectMonitorLog(hostId,type);
        if(monitorLogs!=null)return Result.success(monitorLogs);
        else return Result.instance(ResponseCode.error);
    }


    /**
     * 获取主机SSH工作量信息
     *
     * @param hostId    主机ID
     * @return
     */
    @ApiOperation(value = "获取主机SSH工作量信息", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/ssh/task/sum", method = RequestMethod.GET)
    public Result list(@RequestParam(required = true) Long hostId) {
        return Result.success(devService.getTaskLogSum(hostId));
    }

    /**
     * 启停监控主机
     *
     * @param hostId    主机ID
     * @param type      启用/停用
     * @return
     */
    @ApiOperation(value = "获取主机SSH工作量信息", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/ssh/host/monitor", method = RequestMethod.GET)
    public Result monitor(@RequestParam(required = true) Long hostId,
                        @RequestParam(required = true) Integer type) {
        return null;
    }


}
