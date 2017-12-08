package com.liug.service.impl;

import com.github.pagehelper.PageHelper;
import com.liug.common.util.Result;
import com.liug.dao.ManagerProblemMapper;
import com.liug.dao.ManagerWorkMapper;
import com.liug.dao.SapScriptMapper;
import com.liug.dao.SapScriptStaticMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.model.entity.ManagerWork;
import com.liug.model.entity.SapScript;
import com.liug.model.entity.SapScriptStatic;
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
    @Autowired
    private ManagerWorkMapper managerWorkMapper;
    @Autowired
    private SapScriptMapper sapScriptMapper;
    @Autowired
    private SapScriptStaticMapper sapScriptStaticMapper;

    @Override
    public PageInfo selectProblemPage(int page, int rows, String sort,  String order ,Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = managerProblemMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<ManagerProblem> problems = managerProblemMapper.selectAll(sort, order, begin, end);
        PageInfo pageInfo = new PageInfo(counts, problems);
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

    @Override
    public PageInfo selectWorkPage(int page, int rows, String sort, String order, Date begin, Date end) {
        logger.info("page = [" + page + "], rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], begin = [" + begin + "], end = [" + end + "]");
        int counts = managerWorkMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<ManagerWork> works = managerWorkMapper.selectAll(sort, order, begin, end);
        PageInfo pageInfo = new PageInfo(counts, works);
        return pageInfo;
    }

    @Override
    public long addWork(String summary, String level, String type) {
        ManagerWork managerWork = new ManagerWork();
        managerWork.setSummary(summary);
        managerWork.setLevel(level);
        managerWork.setType(type);
        managerWork.setCreatedate(new Date(System.currentTimeMillis()));
        return managerWorkMapper.insert(managerWork);
    }

    @Override
    public long dealWork(ManagerWork managerWork) {
        return managerWorkMapper.update(managerWork);
    }

    @Override
    public long deleteWork(long id) {

        return managerWorkMapper.deleteById(id);
    }

    @Override
    public Result selectSapScript() {
        return Result.success(sapScriptMapper.selectAll());
    }

    @Override
    public Result selectSapScriptStatic() {
        return Result.success(sapScriptStaticMapper.selectAll());
    }

    @Override
    public Result generator(SapScript sapScript) {
        String content="";
        content+="\n"+sapScriptStaticMapper.selectByCode("param").getContent();
        content+="\n"+sapScriptStaticMapper.selectByCode("workspace").getContent();
        content+="\n"+sapScriptStaticMapper.selectByCode("gui").getContent();
        content+="\n"+sapScriptStaticMapper.selectByCode("word_create").getContent();
        //获取tcode
        if(sapScript.getTcode()!=null) {
            String[] tcodes = sapScript.getTcode().split("\\*");
            for (String tcode : tcodes) {
                SapScriptStatic scriptStatic =sapScriptStaticMapper.selectByCode(tcode);
                if (scriptStatic!=null) content+="\n"+scriptStatic.getContent();
            }
        }
        content+="\n"+sapScriptStaticMapper.selectByCode("word_end").getContent();
        content+="\n"+sapScriptStaticMapper.selectByCode("function").getContent();
        return Result.success(content);
    }
}
