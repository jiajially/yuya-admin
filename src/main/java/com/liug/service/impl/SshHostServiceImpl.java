package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.ResponseCode;
import com.liug.dao.SshHostMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshHost;
import com.liug.service.SshHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Service
@Transactional
public class SshHostServiceImpl implements SshHostService {

    private static final Logger logger = LoggerFactory.getLogger(SshHostService.class);
    @Autowired
    private SshHostMapper sshHostMapper;


    @Override
    public long insertHost(SshHost sshHost) {
        int _existsCounts = sshHostMapper.selectCountsExists(sshHost.getHost(),sshHost.getPort(),sshHost.getUsername());
        if (_existsCounts > 0)return ResponseCode.host_already_exist.getCode();
        else sshHostMapper.insert(sshHost);
        return sshHost.getId();
    }

    @Override
    public long updateHost(SshHost sshHost) {
        sshHostMapper.update(sshHost);
        return sshHost.getId();
    }

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String host, String username, String envPath) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], host = [" + host + "], username = [" + username + "], envPath = [" + envPath + "]");
        int counts = sshHostMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SshHost> sshHosts = sshHostMapper.selectAll(sort, order, host, username, envPath);
        PageInfo pageInfo = new PageInfo(counts, sshHosts);
        return pageInfo;
    }

    @Override
    public List<SelectContnet> select() {
        List<SshHost> sshHosts = sshHostMapper.selectAll("host", "asc", null, null, null);
        List<SelectContnet> selectContnets = new ArrayList<SelectContnet>();
        for (SshHost sshHost:sshHosts) {
            SelectContnet selectContnet = new SelectContnet();
            selectContnet.setId(sshHost.getId());
            selectContnet.setText(sshHost.getUsername()+"@"+sshHost.getHost()+":"+sshHost.getPort());
            if(sshHost.isEnable()&&sshHost.isValid()) selectContnets.add(selectContnet);
        }
        return selectContnets;
    }

    @Override
    public SshHost selectById(long id) {
        SshHost sshHost = sshHostMapper.selectById(id);
        return sshHost;
    }

    @Override
    public long deleteById(long id) {
        return sshHostMapper.deleteById(id);
    }


    @Override
    public SshResult validHost(SshHost sshHost) {

        SshResult sshResult = new SshResult();
        //验证数据重复性
        int _existsCounts = sshHostMapper.selectCountsExists(sshHost.getHost(),sshHost.getPort(),sshHost.getUsername());
        if (sshHost.getId()<0&&_existsCounts > 0)sshResult.setExitStatus(ResponseCode.host_already_exist.getCode());
        else {
            //通过SSH获取PATH参数
            sshResult = Commond.getEnvPath(sshHost);
            if (sshResult.getExitStatus() == 1) {
                //验证成功,设置host参数
                sshHost.setEnvPath(sshResult.getContent());
                sshHost.setValid(true);
                if (sshHost.getId() >= 0) {
                    sshHostMapper.update(sshHost);
                } else {
                    sshHostMapper.insert(sshHost);
                }
                logger.info(sshHost.toString());
            }
        }
        return sshResult;
    }

    @Override
    public long enableHost(long id, boolean isEnable) {
        long _temp = id;
        SshHost sshHost = sshHostMapper.selectById(id);
        if (!sshHost.isEnable()&&!isEnable) _temp = -1;
        else {
            sshHost.setEnable(isEnable);
            sshHostMapper.update(sshHost);
        }
        return _temp;
    }
}
