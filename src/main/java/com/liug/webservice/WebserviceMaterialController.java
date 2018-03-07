package com.liug.webservice;

import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.dao.DocumentMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.service.DocumentService;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    DocumentMapper documentMapper;

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public Result getFile(HttpServletResponse resp, @RequestParam("id") long id) throws UnsupportedEncodingException {
        Document document = documentService.selectById(id);

        // Get your file stream from wherever.
        String path = FileUtil.getProjectPath() + "/" + document.getUrl();
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(document.getSummary(), "utf-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
       /* try {
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
        }*/
        try {
            FileUtil.fileDownloader(new BufferedInputStream(new FileInputStream(file)), resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }
    }


    /**
     * 查询文档列表
     *
     * @param type 类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result documentlist(@RequestParam(required = true) String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        PageInfo pageInfo = null;
        try {
            pageInfo = documentService.selectPage(1, 99999, "createtime", "desc", type, sdf.parse("1970-01-01"), sdf.parse("2099-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Result.success(pageInfo.getRows());
    }

    /**
     * 查询文档列表
     *
     * @param sid sid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBySid", method = RequestMethod.GET)
    public Result documentlistBySid(@RequestParam(required = true) long sid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        List<Document> list = documentMapper.selectBySid(sid);

        return Result.success(list);
    }

    /**
     * 查询文档列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    public Result documentall() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        List<Document> list = documentMapper.selectAll("type,id", null, null, null, null);

        return Result.success(list);
    }

    /**
     * linkSidList
     *
     * @param sid sid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "linkListbySid", method = RequestMethod.GET)
    public Result documentlinkSid(@RequestParam(required = true) long sid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        List<Document> list = documentMapper.selectBySid(sid);

        return Result.success(list);
    }

    /**
     * linkFiles
     *
     * @param sid sid
     * @param ids list
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "linkFiles", method = RequestMethod.GET)
    public Result documentlinkSid(@RequestParam(required = true) long sid, @RequestParam(required = true)long [] ids) {

        for (long id : ids) {
            try {
                documentMapper.insertDocumentSid(id, sid);
            }catch (Exception e){

            };
        }

        return Result.success();
    }
    /**
     * linkDelete
     * @param sid sid
     * @param id id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "linkDelete", method = RequestMethod.GET)
    public Result documentlinkDelete(@RequestParam(required = true) long sid, @RequestParam(required = true)long  id) {

        return Result.success(documentMapper.deleteDocumentSid(id, sid));
    }

    // 单文件上传
    @RequestMapping(value = "local/add", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadLocal(@RequestParam("file") MultipartFile file,
                              @RequestParam(required = false) String summary,
                              @RequestParam(required = false) String tag

    ) {
        return documentService.addLocalDetail(file, summary, tag);

    }

    // 单文件上传
    @RequestMapping(value = "system/add", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadSystem(@RequestParam("file") MultipartFile file,
                               @RequestParam(required = false) String summary,
                               @RequestParam(required = false) String tag

    ) {
        return documentService.addSystemDetail(file, summary, tag);

    }


    /**
     * 删除文档
     *
     * @param id 文档id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(required = true) Long id) {
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
            @RequestParam(required = true) String summary,
            @RequestParam(required = true) String url,
            @RequestParam(required = false) String tag
    ) {
        return Result.success(documentService.addOnline(summary, url, tag));
    }


}
