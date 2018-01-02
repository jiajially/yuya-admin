package com.liug.webservice;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.FileUtil;
import com.liug.common.util.ResponseCode;
import com.liug.common.util.Result;
import com.liug.common.util.StringUtil;
import com.liug.dao.SapScriptMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.service.DocumentService;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import com.liug.service.SshScriptService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Controller
@RequestMapping("webservice/material")
public class WebserviceMaterialController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceMaterialController.class);

    @Autowired
    DocumentService documentService;

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void getFile(HttpServletResponse resp,@RequestParam("id")long id) throws UnsupportedEncodingException {
        Document document =documentService.selectById(id);

        // Get your file stream from wherever.
        String path = FileUtil.getProjectPath()+"/" + document.getUrl();
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(document.getSummary(),"utf-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        String qqq1;
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
     * 查询文档列表
     *
     * @param type      类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result documentlist( @RequestParam(required = true) String type)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        PageInfo pageInfo = null;
        try {
            pageInfo = documentService.selectPage(1,99999,"createtime","desc",type, sdf.parse("1970-01-01"),sdf.parse("2099-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Result.success(pageInfo.getRows());
    }

    // 单文件上传
    @RequestMapping(value = "local/add",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadLocal(@RequestParam("file") MultipartFile file
    ) {
        return documentService.addLocal(file);

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
    public Result delete(@RequestParam(required = true) Long id)  {
        return documentService.delete(id);
    }

    /**
     * 新增在线文档
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "online/add", method = RequestMethod.POST)
    public Result queryScriptById(
            @RequestParam(required = true) String  summary,
            @RequestParam(required = true) String  url
    ) {
        return Result.success(documentService.addOnline(summary,url));
    }


}
