package com.liug.white.service;

import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SshScript;

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
