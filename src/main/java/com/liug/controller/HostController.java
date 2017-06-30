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
@Api(value = "主机模块")
@Controller
@RequestMapping("host")
public class HostController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HostController.class);
    @Autowired
    private SshHostService sshHostService;

    @ApiOperation(value = "跳转至主机管理模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "host", method = RequestMethod.GET)
    public String host() {
        return "ssh/host";
    }

    /**
     * 查询主机列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param host      主机
     * @param username  主机用户
     * @param envPath   环境变量
     * @return
     */
    @ApiOperation(value = "查询主机列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int rows,
                         @RequestParam(defaultValue = "host") String sort,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam(defaultValue = "", required = false) String host,
                         @RequestParam(defaultValue = "", required = false) String username,
                         @RequestParam(defaultValue = "", required = false) String envPath) {
        PageInfo pageInfo = sshHostService.selectPage(page, rows, StringUtil.camelToUnderline(sort), order, host, username, envPath);
        return pageInfo;
    }


    /**
     * 查询主机列表-选择框
     *
     */
    @ApiOperation(value = "查询主机列表-选择框", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public List<SelectContnet> select() {
        List<SelectContnet> selectContnets = sshHostService.select();
        return selectContnets;
    }

    /**
     * 新增主机
     *
     * @param host          登录名
     * @param port          端口
     * @param username      主机用户
     * @param password      主机密码
     * @return
     */
    @ApiOperation(value = "新增主机", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@RequestParam(required = true)String host,
                         @RequestParam(required = true)Integer port,
                         @RequestParam(required = true)String username,
                         @RequestParam(required = true)String password) {
        SshHost sshHost = new SshHost();
        sshHost.setHost(host);
        sshHost.setPort(port);
        sshHost.setUsername(username);
        sshHost.setPassword(password);
        sshHost.setStatus(0);
        long id = sshHostService.insertHost(sshHost);
        if(id == ResponseCode.host_already_exist.getCode())return Result.instance(ResponseCode.host_already_exist);
        else return Result.success(id);
    }
    /**
     * 更新主机
     *
     * @param host          登录名
     * @param port          端口
     * @param username      主机用户
     * @param password      主机密码
     * @return
     */
    @ApiOperation(value = "更新主机", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(required = true)Long id,
                         @RequestParam(required = true)String host,
                         @RequestParam(required = true)Integer port,
                         @RequestParam(required = true)String username,
                         @RequestParam(required = true)String password) {
        SshHost sshHost = new SshHost();
        sshHost.setId(id);
        sshHost.setHost(host);
        sshHost.setPort(port);
        sshHost.setUsername(username);
        sshHost.setPassword(password);
        return Result.success(sshHostService.updateHost(sshHost));
    }

    /**
     * 根据id查询
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "查询主机", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Result insert(@RequestParam long id) {
        SshHost sshHost =sshHostService.selectById(id);
        return Result.success(sshHost);
    }

    /**
     * 根据id删除
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "根据id删除", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result delete(@RequestParam long id) {
        return Result.success(sshHostService.deleteById(id));
    }

    /**
     * 验证主机有效性
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "验证主机有效性", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "valid", method = RequestMethod.POST)
    public Result validHost(@RequestParam long id,
                            @RequestParam String host,
                            @RequestParam Integer port,
                            @RequestParam String username,
                            @RequestParam String password) {
        Result result;
        SshResult sshResult;
        SshHost sshHost = new SshHost();
        sshHost.setId(id);
        sshHost.setHost(host);
        sshHost.setPort(port);
        sshHost.setPassword(password);
        sshHost.setUsername(username);
        sshResult = sshHostService.validHost(sshHost);

        if (sshResult.getExitStatus() == 1) result=Result.success(sshResult);
        else if (sshResult.getExitStatus()==ResponseCode.host_already_exist.getCode())
            result = Result.instance(ResponseCode.host_already_exist);
        else result = Result.instance(ResponseCode.host_valid_fail.getCode(), sshResult.getContent());
        return  result;
    }


    /**
     * 禁用主机
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "禁用主机", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "forbiddenHost", method = RequestMethod.GET)
    public Result forbiddenHost(@RequestParam long id) {
        Result result;
        long temp = sshHostService.enableHost(id,false);
        if(temp>=0) result = Result.success(temp);
        else {
            result = Result.instance(ResponseCode.host_valid_enable_fail);
        }
        return result;
    }

    /**
     * 启用主机
     *
     * @param id          id
     * @return
     */
    @ApiOperation(value = "启用主机", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "enableHost", method = RequestMethod.GET)
    public Result enableHost(@RequestParam long id) {
        Result result;
        long temp = sshHostService.enableHost(id,true);
        if(temp>=0) result = Result.success(temp);
        else {
            result = Result.instance(ResponseCode.host_valid_enable_fail);
        }
        return result;
    }

}
