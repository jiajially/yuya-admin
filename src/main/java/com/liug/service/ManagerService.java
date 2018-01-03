package com.liug.service;

import com.liug.common.util.Result;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.model.entity.ManagerWork;
import com.liug.model.entity.SapScript;

import java.util.Date;

/**
 * Created by liugang on 2017/8/12.
 */
public interface ManagerService {

    PageInfo selectProblemPage(int page, int rows, String sort,  String order, Date begin,Date end);

    long addProblem(String summary,String channel,String informer);

    long dealProblem(ManagerProblem managerProblem);

    ManagerProblem getProblemById(long id);

    PageInfo selectWorkPage(int page, int rows, String sort,  String order, Date begin,Date end);

    long addWork(String summary,String level,String type);

    long dealWork(ManagerWork managerWork);

    long deleteWork(long id);

    Result selectSapScript();

    Result selectSapScriptStatic();

    Result generator(SapScript sapScript);

    Result generator(SapScript sapScript, String path);

    Result save(SapScript sapScript);


}
