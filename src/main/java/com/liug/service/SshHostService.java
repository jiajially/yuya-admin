package com.liug.service;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshHost;

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
