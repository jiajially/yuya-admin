package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.common.util.Result;
import com.liug.dao.DocumentMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.Document;
import com.liug.service.DocumentService;
import com.liug.service.SshHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/8/13.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {


    private static final Logger logger = LoggerFactory.getLogger(SshHostService.class);
    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String type, Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], type = [" + type + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = documentMapper.selectCounts(type, begin, end);
        PageHelper.startPage(page, rows);
        List<Document> documents = documentMapper.selectAll(sort, order, type, begin, end);
        PageInfo pageInfo = new PageInfo(counts, documents);
        return pageInfo;
    }

    @Override
    public Document selectById(long id) {
        return documentMapper.selectById(id);
    }

    @Override
    public long addOnline(String summary, String url) {
        if (!url.startsWith("http")) url = "http://" + url;
        Document document = new Document();
        document.setUrl(url);
        document.setSummary(summary);
        document.setType("1");
        return documentMapper.insert(document);
    }

    @Override
    public Result addLocal(MultipartFile file) {
        Result result = saveFile(file, 2);
        Document document;
        if (result.getCode() == Result.success().getCode()) {
            document = (Document) result.getData();
            documentMapper.insert(document);
        }

        return result;
    }

    @Override
    public Result addSys(MultipartFile file) {
        Result result = saveFile(file, 0);
        Document document;
        if (result.getCode() == Result.success().getCode()) {
            document = (Document) result.getData();
            documentMapper.insert(document);
        }

        return result;
    }

    @Override
    public Result delete(long id) {
        Document document = documentMapper.selectById(id);
        if (document != null && document.getId() > 0l) {
            //可以查得到
            if (document.getType() != "1") deleteFile(document.getUrl());
            documentMapper.deleteById(id);
            return Result.success("删除成功");
        }
        return Result.success("文件不存在");
    }

    void deleteFile(String path) {
        File _file = new File(path);
        if (_file.exists()) _file.delete();
    }

    Result saveFile(MultipartFile file, int type) {
        String p_dir = "file/";
        String dir = p_dir + (type == 0 ? "document/sys/" : "document/local/");
        String filename = null;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename().trim();
            String tmp_filename = String.valueOf(System.currentTimeMillis());
            if (isFileExists(tmp_filename, type)) return Result.error("文件已存在");
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                File _tmp = new File(dir + tmp_filename);

                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(_tmp));

                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                return Result.error("上传失败," + e.getMessage());
            } catch (IOException e) {
                return Result.error("上传失败," + e.getMessage());
            }
            Document document = new Document();
            document.setType("" + type);
            document.setSummary(filename);
            document.setUrl(dir + tmp_filename);
            //获取后缀
            String _of = file.getOriginalFilename();
            document.setSuffix(_of.split("\\.")[_of.split("\\.").length - 1]);
            return Result.success(document);
        } else {
            return Result.error("上传失败，因为文件是空的.");
        }

    }

    boolean isFileExists(String filename, int type) {
        //数据库记录数
        int _fileCount = documentMapper.fileCount("" + type, filename);
        //文件目录记录

        String p_dir = "file/";
        String dir = p_dir + (type == 0 ? "document/sys/" : "document/local/");
        if (new File(dir + filename).exists() && _fileCount > 0) return true;
        return false;
    }


}
