package com.liug.controller;

import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.common.util.StringUtil;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshScript;
import com.liug.model.entity.SshTask;
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


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "SSH模块")
@Controller
@RequestMapping("ssh")
public class SshController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SshController.class);
    @Autowired
    private SshTaskService sshTaskService;
    @Autowired
    private SshScriptService sshScriptService;

    @ApiOperation(value = "跳转至SSH管理模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "host", method = RequestMethod.GET)
    public String ssh() {
        return "ssh/host";
    }

    @ApiOperation(value = "跳转至SSH脚本配置管理模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "script", method = RequestMethod.GET)
    public String script() {
        return "ssh/script";
    }

    @ApiOperation(value = "跳转至SSH执行状态模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "task", method = RequestMethod.GET)
    public String task() {
        return "ssh/task";
    }

    /**
     * 查询指令列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param hostname  主机
     * @param cmd       指令
     * @return
     */
    @ApiOperation(value = "查询脚本状态列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "task/list", method = RequestMethod.GET)
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int rows,
                         @RequestParam(defaultValue = "execTime") String sort,
                         @RequestParam(defaultValue = "desc") String order,
                         @RequestParam(defaultValue = "", required = false) String name,
                         @RequestParam(defaultValue = "", required = false) String hostname,
                         @RequestParam(defaultValue = "", required = false) String cmd) {
        PageInfo pageInfo = sshTaskService.selectPage(page, rows, StringUtil.camelToUnderline(sort), order,name, hostname, cmd);
        return pageInfo;
    }

    /**
     * 新增指令
     *
     * @param name          登录名
     * @param host          主机用户
     * @param cmd           主机密码
     * @param startTime     指令
     * @param endTime       备注
     * @param rate          执行结果
     * @return
     */
    @ApiOperation(value = "新增指令", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "script/insert", method = RequestMethod.POST)
    public Result insert(@RequestParam(required = true)String name,
                         @RequestParam(required = true)long host,
                         @RequestParam(required = true)String cmd,
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
        log.info(name+">>>>");
        long id = sshScriptService.insertScript(sshScript);
        if (id == -1) return Result.instance(ResponseCode.code_already_exist);
        else return Result.success(id);
    }


    /**
     * 根据id查询
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "查询task执行结果", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "task/result", method = RequestMethod.GET)
    public Result insert(@RequestParam long id) {
        SshTask sshTask =sshTaskService.selectById(id);
        //log.info(sshTask.toString());
        return Result.success(sshTask);
    }



    /**
     * 查询指令脚本
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param name      脚本名称
     * @param cmd       指令
     * @return
     */
    @ApiOperation(value = "查询指令脚本", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "script/list", method = RequestMethod.GET)
    public PageInfo scriptList(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int rows,
                         @RequestParam(defaultValue = "host") String sort,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam(defaultValue = "", required = false) String name,
                         @RequestParam(defaultValue = "", required = false) String hostname,
                         @RequestParam(defaultValue = "", required = false) String cmd) {
        PageInfo pageInfo = sshScriptService.selectPage(page, rows, StringUtil.camelToUnderline(sort), order, name,hostname, cmd);
        return pageInfo;
    }

    /**
     * 删除指令脚本
     *
     * @param id       id
     * @return
     */
    @ApiOperation(value = "删除指令脚本", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "script/delete", method = RequestMethod.GET)
    public Result scriptDelete(@RequestParam(required = true) long id) {
        return Result.success(sshScriptService.deleteScript(id));
    }

    /**
     * 根据id查询
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "查询脚本配置", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "script/detail", method = RequestMethod.GET)
    public Result queryScriptById(@RequestParam long id) {
        SshScript sshScript =sshScriptService.selectById(id);
        return Result.success(sshScript);
    }

    /**
     * 停止调度
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "停止调度", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "script/stop", method = RequestMethod.GET)
    public Result stopScript(@RequestParam long id) {
        return Result.success(sshScriptService.stopScript(id));
    }

}
