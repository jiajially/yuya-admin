package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.dao.SshScriptMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SshScript;
import com.liug.service.SshScriptService;
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
public class SshScriptServiceImpl implements SshScriptService {
    private static final Logger logger = LoggerFactory.getLogger(SshScriptService.class);
    @Autowired
    private SshScriptMapper sshScriptMapper;

    @Override
    public long insertScript(SshScript sshScript) {
        int count = sshScriptMapper.selectAll("name","asc" ,sshScript.getName(),null,null).size();
        if (count>0)
            return -1;
        else {
            sshScriptMapper.insert(sshScript);
            return sshScript.getId();
        }
    }

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String name,String hostname, String cmd) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], hostname = [" + hostname + "],name = [" + name + "],  cmd = [" + cmd + "]");
        int counts = sshScriptMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SshScript> sysTasks = sshScriptMapper.selectAll(sort, order, name,hostname, cmd);
        PageInfo pageInfo = new PageInfo(counts, sysTasks);
        return pageInfo;
    }

    @Override
    public SshScript selectById(long id) {
        SshScript SshScript = sshScriptMapper.selectById(id);
        return SshScript;
    }

    @Override
    public long deleteScript(long id) {
        return sshScriptMapper.deleteById(id);
    }

    @Override
    public long stopScript(long id) {
        return sshScriptMapper.updateEndtime2CurrentById(id);
    }


}
