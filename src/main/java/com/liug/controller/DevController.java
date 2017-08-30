package com.liug.controller;

import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.dao.SshTaskMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.CharRecg;
import com.liug.model.entity.HomePage;
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

    @ApiOperation(value = "跳转至Webconsole", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "console", method = RequestMethod.GET)
    public String console() {
        return "dev/console";
    }

    @ApiOperation(value = "跳转至Homepage", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "homepage", method = RequestMethod.GET)
    public String homepage() {
        return "dev/homepage";
    }

    @ApiOperation(value = "跳转至vbtest1", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "vbtest1", method = RequestMethod.GET)
    public String vbtestdemo1() {
        return "vbtest/demo1";
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
    public Result queryScriptById(@RequestParam long id,@RequestParam String file,@RequestParam String word) {
        CharRecg charRecg;
        if (word==null || word.trim().length()==0)charRecg = devService.loadfile(id,file,"!2#4%6&8(0_=");
        else charRecg = devService.loadfile(id,file,word);
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
    @RequestMapping(value = "monitor/log", method = RequestMethod.GET)
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
    @RequestMapping(value = "ssh/task/sum", method = RequestMethod.GET)
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
    @ApiOperation(value = "启停监控主机", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "ssh/host/monitor", method = RequestMethod.GET)
    public Result monitor(@RequestParam(required = true) Long hostId,  @RequestParam(required = true) Integer type) {
        return Result.success(devService.monitor(hostId,type));
    }

    /**
     * 新增Homepage unit
     *
     * @param host          主机
     * @param title         标题
     * @param description   描述
     * @param unit          单位
     * @param cmd           指令
     * @return
     */
    @ApiOperation(value = "新增Homepage unit", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "homepage/insert", method = RequestMethod.POST)
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
     * 获取Homepage unit
     *
     * @return
     */
    @ApiOperation(value = "获取Homepage unit", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "homepage/select", method = RequestMethod.GET)
    public Result select() {
        return Result.success(devService.getHomePage());
    }


    /**
     * Homepage toolbox
     *
     * @return
     */
    @ApiOperation(value = "Homepage toolbox", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "homepage/toolbox", method = RequestMethod.GET)
    public Result toolbox(@RequestParam(required = true)Long id,
                          @RequestParam(required = true)Integer type) {
         //   return null;
        return Result.success(devService.toolbox(id,type));
    }

    /**
     * Homepage flash
     *
     * @return
     */
    @ApiOperation(value = "Homepage flash", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "homepage/flash", method = RequestMethod.GET)
    public Result toolbox() {
        //   return null;
        return devService.flash();
    }

}
