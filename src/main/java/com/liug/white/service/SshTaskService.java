package com.liug.white.service;

import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SshTask;

/**
 * Created by liugang on 2017/5/30.
 */
public interface SshTaskService {


    long insertTask(SshTask sshTask);

    PageInfo selectPage(int page, int rows, String sort, String order,String name, String hostname, String cmd);

    SshTask selectById(long id);

}
