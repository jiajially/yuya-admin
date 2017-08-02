package com.liug.service;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshHost;
import com.liug.model.entity.SshHostDetail;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
public interface SshHostService {


    long insertHost(SshHost sshHost, SshHostDetail sshHostDetail);

    long updateHost(SshHost sshHost, SshHostDetail sshHostDetail);

    PageInfo selectPage(int page, int rows, String sort, String order, String host, String username, String envPath);

    List<SelectContnet> select();

    SshHost selectById(long id);

    long deleteById(long id);

    SshResult validHost(SshHost sshHost, SshHostDetail sshHostDetail);

    long enableHost(long id,boolean isEnable);

    List<SelectContnet> getOs();

    List<SelectContnet> getSw();
}
