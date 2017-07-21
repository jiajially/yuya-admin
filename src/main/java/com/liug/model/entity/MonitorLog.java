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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getRecTime() {
        return recTime;
    }

    public void setRecTime(Date recTime) {
        this.recTime = recTime;
    }

    @Override
    public String toString() {
        return "MonitorLog{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", jobId=" + jobId +
                ", result='" + result + '\'' +
                ", recTime=" + recTime +
                '}';
    }
}
