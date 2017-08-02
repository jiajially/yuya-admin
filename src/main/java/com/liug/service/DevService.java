package com.liug.service;

import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshChartResult;
import com.liug.common.util.Result;
import com.liug.model.entity.CharRecg;
import com.liug.model.entity.HomePage;
import com.liug.model.entity.MonitorLog;
import com.liug.model.entity.SshHostOs;

import java.util.List;

/**
 * Created by liugang on 2017/7/2.
 */
public interface DevService {

    CharRecg loadfile(long id, String path,String word);

    List<MonitorLog> selectMonitorLog(long hostId, Integer type);

    List<SshChartResult> getTaskLogSum(Long hostId);

    String monitor(Long hostId,int type);

    Result addHomePage(HomePage homePage);

    List<HomePage> getHomePage();

    Result toolbox(long id,int type);

    public Result flash();

}
