package com.liug.white.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.black.dao.SshTaskMapper;
import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SshTask;
import com.liug.white.service.SshTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Service
@Transactional
public class SshTaskServiceImpl implements SshTaskService {
    private static final Logger logger = LoggerFactory.getLogger(SshTaskService.class);
    @Autowired
    private SshTaskMapper sshTaskMapper;

    @Override
    public long insertTask(SshTask sshTask) {
        sshTaskMapper.insert(sshTask);
        return sshTask.getId();
    }

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order,String name, String hostname, String cmd) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], name = [" + name + "], hostname = [" + hostname + "], cmd = [" + cmd + "]");
        int counts = sshTaskMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SshTask> sysTasks = sshTaskMapper.selectAll(sort, order,name, hostname, cmd);
        PageInfo pageInfo = new PageInfo(counts, sysTasks);
        return pageInfo;
    }

    @Override
    public SshTask selectById(long id) {
        SshTask sshTask = sshTaskMapper.selectById(id);
        return sshTask;
    }


}
