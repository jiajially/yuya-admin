package com.liug.service;

import com.liug.model.entity.CharRecg;

/**
 * Created by liugang on 2017/7/2.
 */
public interface DevService {

    CharRecg loadfile(long id, String path);
}
