package com.liug.service;

import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshScript;

/**
 * Created by liugang on 2017/5/30.
 */
public interface SshScriptService {


    long insertScript(SshScript sshTask);

    PageInfo selectPage(int page, int rows, String sort, String order, String name,String hostname, String cmd);

    SshScript selectById(long id);

    long deleteScript(long id);

    long stopScript(long id);

}
