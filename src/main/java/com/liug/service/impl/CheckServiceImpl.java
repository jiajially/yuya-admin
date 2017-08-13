package com.liug.service.impl;

import com.liug.common.util.FileStruct;
import com.liug.common.util.FileUtil;
import com.liug.model.dto.PageInfo;
import com.liug.service.CheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugang on 2017/8/13.
 */
@Service
@Transactional
public class CheckServiceImpl implements CheckService {
    @Override
    public PageInfo selectBackupPage(int page, int rows, String sort, String order, String path) {
        PageInfo pageInfo = new PageInfo(0,"");
        try {
            List<FileStruct> fileStructList = FileUtil.readfileList(path);

            if((page*rows-1)>=fileStructList.size()) {
                List<FileStruct> result = fileStructList.subList((page - 1) * rows, fileStructList.size());
                pageInfo =new PageInfo(fileStructList.size(),result);
            }else{
                List<FileStruct> result = fileStructList.subList((page - 1) * rows, page*rows);
                pageInfo =new PageInfo(fileStructList.size(),result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
}
