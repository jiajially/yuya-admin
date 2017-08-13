package com.liug.controller;

import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.service.ManagerService;
import com.sun.org.apache.regexp.internal.RE;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "管理工作")
@Controller
@RequestMapping("manager")
public class ManagerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private ManagerService managerService;


    @ApiOperation(value = "problem", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "problem", method = RequestMethod.GET)
    public String dashboard() {
        return "manager/problem";
    }


    /**
     * 查询问题列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param begin     开始
     * @param end       结束
     * @return
     */
    @ApiOperation(value = "查询问题列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "problem/list", method = RequestMethod.GET)
    public PageInfo problemList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "30") int rows,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(required = false) String begin,
                                @RequestParam(required = false) String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_begin, date_end;
        try {
            date_begin = sdf.parse(begin);
        } catch (ParseException e) {
            date_begin = new Date();
            date_begin.setTime(0);
        }
        try {
            date_end = sdf.parse(end);
        } catch (ParseException e) {
            date_end = new Date(System.currentTimeMillis());
        }

        PageInfo pageInfo = managerService.selectProblemPage(page,rows,sort,order,date_begin,date_end);
        return pageInfo;
    }
    /**
     * 根据id查problem
     *
     * @param id      id
     *
     * @return
     */
    @ApiOperation(value = "查询问题列表", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/detail", method = RequestMethod.GET)
    public Result problemList(@RequestParam(required = true) int id) {
        return Result.success(managerService.getProblemById(id));
    }
    /**
     * 添加问题
     *
     * @param summary      起始页码
     * @param channel      分页大小
     * @param informer     排序字段
     * @return
     */
    @ApiOperation(value = "添加问题", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/insert", method = RequestMethod.POST)
    public Result problemList(
                            @RequestParam(required = true) String summary,
                            @RequestParam(required = true) String channel,
                            @RequestParam(required = true) String informer
                                ) {
        long id = managerService.addProblem(summary,channel,informer);
        if (id>0)return  Result.success(id);
        else return Result.error();
    }

    /**
     * 处理问题
     *
     * @param id      起始页码
     * @return
     */
    @ApiOperation(value = "处理问题", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/deal", method = RequestMethod.POST)
    public Result problemList(
            @RequestParam(required = true) long id,
            @RequestParam(required = false) String detail,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String needtime,
            @RequestParam(required = false) String needresources,
            @RequestParam(required = false) String result,
            @RequestParam(required = false) String dealer,
            @RequestParam(required = false) String recorder
    ) {
        ManagerProblem problem = new ManagerProblem();
        problem.setId(id);
        problem.setDetail(detail);
        problem.setType(type);
        problem.setLevel(level);
        problem.setSystem(system);
        problem.setNeedtime(needtime);
        problem.setNeedresources(needresources);
        problem.setResult(result);
        problem.setDealer(dealer);
        problem.setRecorder(recorder);
        if (managerService.dealProblem(problem)>0)return  Result.success();
        else return Result.error();
    }

}
