package com.liug.service;

import com.liug.common.ssh.SshChartResult;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.CharRecg;
import com.liug.model.entity.MonitorLog;

import java.util.List;

/**
 * Created by liugang on 2017/7/2.
 */
public interface DevService {

    CharRecg loadfile(long id, String path);

    List<MonitorLog> selectMonitorLog(long hostId, Integer type);

    List<SshChartResult> getTaskLogSum(Long hostId);

    String monitor(Long hostId,int type);
}
