package com.liug.service;


import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by liugang on 2017/8/13.
 */
public interface DocumentService {


    PageInfo selectPage(int page, int rows, String sort, String order, String type, Date begin, Date end);

    Document selectById(long id);

    long addOnline(String summary,String url);

    Result addLocal(MultipartFile file);

    Result addSys(MultipartFile file);

    Result delete(long id);

}
