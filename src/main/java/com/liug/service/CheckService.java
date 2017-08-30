package com.liug.service;


import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.Check;

import java.util.Date;

/**
 * Created by liugang on 2017/8/13.
 */
public interface CheckService {

    PageInfo selectBackupPage(int page, int rows, String sort, String order, String path);

    PageInfo selectPage(int page, int rows, String sort, String order, String type,Date begin,Date end);

    Check selectById(long id);

}
