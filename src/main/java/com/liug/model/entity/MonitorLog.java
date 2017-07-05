package com.liug.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/7/4.
 */
public class MonitorLog {
    // id :
    private Long id;
    //监控主机id
    Long hostId;
    // JobId :
    private Long jobId;
    //监控结果 如果是cpu监控可以看做一个平均值 内存 cpu 是一个百分比的值
    String result;
    // rec_time :记录时间
    private Date recTime;
}
