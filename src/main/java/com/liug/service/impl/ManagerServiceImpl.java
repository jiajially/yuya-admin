package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.dao.ManagerProblemMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/8/12.
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
    @Autowired
    private ManagerProblemMapper managerProblemMapper;

    @Override
    public PageInfo selectProblemPage(int page, int rows, String sort,  String order ,Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = managerProblemMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<ManagerProblem> sshHosts = managerProblemMapper.selectAll(sort, order, begin, end);
        PageInfo pageInfo = new PageInfo(counts, sshHosts);
        return pageInfo;
    }

    @Override
    public long addProblem(String summary, String channel, String informer) {
        ManagerProblem managerProblem = new ManagerProblem();
        managerProblem.setChannel(channel);
        managerProblem.setSummary(summary);
        managerProblem.setInformer(informer);
        managerProblem.setDate(new Date(System.currentTimeMillis()));
        return managerProblemMapper.insert(managerProblem);
    }

    @Override
    public long dealProblem(ManagerProblem managerProblem) {
        return managerProblemMapper.update(managerProblem);
    }

    @Override
    public ManagerProblem getProblemById(long id) {
        return managerProblemMapper.selectById(id);
    }
}
