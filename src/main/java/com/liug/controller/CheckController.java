package com.liug.controller;

import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.scheduler.CheckScheduleJob;
import com.liug.service.CheckService;
import com.liug.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "例行检查")
@Controller
@RequestMapping("check")
public class CheckController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;

    @Autowired
    private CheckScheduleJob job;


    @ApiOperation(value = "backup", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "backup", method = RequestMethod.GET)
    public String backup() {
        return "check/backup";
    }

    @ApiOperation(value = "error", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String error() {
        return "check/error";
    }

    @ApiOperation(value = "database", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "database", method = RequestMethod.GET)
    public String database() {
        return "check/database";
    }

    @ApiOperation(value = "space", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "space", method = RequestMethod.GET)
    public String space() {
        return "check/space";
    }

    @ApiOperation(value = "performance", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "performance", method = RequestMethod.GET)
    public String prformaence() {
        return "check/performance";
    }

    @ApiOperation(value = "batch", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "batch", method = RequestMethod.GET)
    public String batch() {
        return "check/batch";
    }

    /**
     * 查询文件列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @return
     */
    @ApiOperation(value = "查询文件列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "list1", method = RequestMethod.GET)
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int rows,
                         @RequestParam(defaultValue = "host") String sort,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam(defaultValue = "", required = false) String path) {
        PageInfo pageInfo = checkService.selectBackupPage(page,rows,sort,order, FileUtil.getProjectPath()+"/file/"+path);
        return pageInfo;
    }


    /**
     * 读取例行检查数据
     *
     * @param id        读取id
     * @return
     */
    @ApiOperation(value = "读取例行检查数据", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Result queryScriptById(@RequestParam long id) {
        return Result.success(checkService.selectById(id));

    }


    /**
     * 查询例行检查列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param type      类型
     * @return
     */
    @ApiOperation(value = "查询例行检查列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo checklist(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int rows,
            @RequestParam(defaultValue = "exectime") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "1970-01-01") String begin,
            @RequestParam(defaultValue = "2099-01-01") String end,
            @RequestParam(required = true) String type)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        PageInfo pageInfo = null;
        try {
            pageInfo = checkService.selectPage(page,rows,sort,order,type, sdf.parse(begin),sdf.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pageInfo;
    }


    /**
     * 读取例行检查详情
     *
     * @return
     */
    @ApiOperation(value = "数据初始化", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public Result checkDetail() {
        try {
            job.initFileData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Result.success();
    }

}
