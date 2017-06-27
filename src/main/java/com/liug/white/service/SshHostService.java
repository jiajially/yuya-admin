package com.liug.white.service;

import com.liug.black.common.ssh.SelectContnet;
import com.liug.black.common.ssh.SshResult;
import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SshHost;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
public interface SshHostService {


    long insertHost(SshHost sshHost);

    long updateHost(SshHost sshHost);

    PageInfo selectPage(int page, int rows, String sort, String order, String host, String username, String envPath);

    List<SelectContnet> select();

    SshHost selectById(long id);

    long deleteById(long id);

    SshResult validHost(SshHost sshHost);

    long enableHost(long id,boolean isEnable);

}
