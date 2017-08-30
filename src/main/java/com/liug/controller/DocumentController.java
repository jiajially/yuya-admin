package com.liug.controller;

import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.Document;
import com.liug.scheduler.CheckScheduleJob;
import com.liug.service.CheckService;
import com.liug.service.DocumentService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "技术文档")
@Controller
@RequestMapping("document")
public class DocumentController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private DocumentService documentService;

    @ApiOperation(value = "sys", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "sys", method = RequestMethod.GET)
    public String sys() {
        return "document/sys";
    }

    @ApiOperation(value = "online", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "online", method = RequestMethod.GET)
    public String online() {
        return "document/online";
    }

    @ApiOperation(value = "local", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "local", method = RequestMethod.GET)
    public String local() {
        return "document/local";
    }



    /**
     * 初始化目录
     *
     * @return
     */
    @ApiOperation(value = "初始化目录", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "initDir", method = RequestMethod.GET)
    public Result initDir() {
       FileUtil.initDictionary();
        return Result.success();
    }


    /**
     * 查询文档列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param type      类型
     * @return
     */
    @ApiOperation(value = "查询文档列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo documentlist(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int rows,
            @RequestParam(defaultValue = "createtime") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "1970-01-01") String begin,
            @RequestParam(defaultValue = "2099-01-01") String end,
            @RequestParam(required = true) String type)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        PageInfo pageInfo = null;
        try {
            pageInfo = documentService.selectPage(page,rows,sort,order,type, sdf.parse(begin),sdf.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 新增在线文档
     *
     * @return
     */
    @ApiOperation(value = "新增在线文档", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "online/insert", method = RequestMethod.POST)
    public Result queryScriptById(
            @RequestParam(required = true) String  summary,
            @RequestParam(required = true) String  url
            ) {
        return Result.success(documentService.addOnline(summary,url));
    }

    // 单文件上传
    @RequestMapping(value = "local/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadLocal(@RequestParam("document-local-file") MultipartFile file
    ) {
        return documentService.addLocal(file);

    }
    // 单文件上传
    @RequestMapping(value = "sys/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadSys(@RequestParam("document-sys-file") MultipartFile file
    ) {
        return documentService.addSys(file);

    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void getFile(HttpServletResponse resp,@RequestParam("filename")String filename,@RequestParam("type")String type) throws UnsupportedEncodingException {

        // Get your file stream from wherever.
        String path = "document/" + type + "/" +filename;
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(filename,"utf-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            os.close();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文档
     *
     * @param id      文档id
     * @return
     */
    @ApiOperation(value = "删除文档", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result documentlist(@RequestParam(required = true) Long id)  {
        return documentService.delete(id);
    }



}
