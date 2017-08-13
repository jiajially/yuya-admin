package com.liug.service;


import com.liug.model.dto.PageInfo;

import java.util.Date;

/**
 * Created by liugang on 2017/8/13.
 */
public interface CheckService {

    PageInfo selectBackupPage(int page, int rows, String sort, String order, String path);

}
