package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.common.util.FileStruct;
import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.dao.CheckMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.Check;
import com.liug.service.CheckService;
import com.liug.service.SshHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/8/13.
 */
@Service
@Transactional
public class CheckServiceImpl implements CheckService {


    private static final Logger logger = LoggerFactory.getLogger(SshHostService.class);
    @Autowired
    private CheckMapper checkMapper;
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

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String type, Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], type = [" + type + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = checkMapper.selectCounts(type,begin,end);
        PageHelper.startPage(page, rows);
        List<Check> checks = checkMapper.selectAll(sort, order, type,begin,end);
        PageInfo pageInfo = new PageInfo(counts, checks);
        return pageInfo;
    }

    @Override
    public Check selectById(long id) {

        return checkMapper.selectById(id);
    }
}
