package com.liug.controller;

import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
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
@Api(value = "管理工作")
@Controller
@RequestMapping("check")
public class CheckController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;


    @ApiOperation(value = "backup", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "backup", method = RequestMethod.GET)
    public String backup() {
        return "check/backup";
    }

    /**
     * 查询主机列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @return
     */
    @ApiOperation(value = "查询文件列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int rows,
                         @RequestParam(defaultValue = "host") String sort,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam(defaultValue = "", required = false) String path) {
        PageInfo pageInfo = checkService.selectBackupPage(page,rows,sort,order,path);
        return pageInfo;
    }


    /**
     * 读取指定路径文件
     *
     * @param path        文件路径
     * @return
     */
    @ApiOperation(value = "读取指定路径文件", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "backup/detail", method = RequestMethod.GET)
    public Result queryScriptById(@RequestParam String path) {
        String tmp = "";
        System.out.println(path);
        try {
            InputStream is = IndexController.class.getClassLoader().getResourceAsStream(path);
            tmp = IOUtils.toString(is,"GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(tmp);

    }

}
