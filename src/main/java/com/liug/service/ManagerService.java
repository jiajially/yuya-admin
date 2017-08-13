package com.liug.service;

import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;

import java.util.Date;

/**
 * Created by liugang on 2017/8/12.
 */
public interface ManagerService {

    PageInfo selectProblemPage(int page, int rows, String sort,  String order, Date begin,Date end);

    long addProblem(String summary,String channel,String informer);

    long dealProblem(ManagerProblem managerProblem);

    ManagerProblem getProblemById(long id);

}
