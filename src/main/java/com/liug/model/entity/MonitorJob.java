package com.liug.model.entity;

/**
 * Created by liugang on 2017/7/4.
 */
public class MonitorJob {
    // id :
    private Long id;
    //监控主机
    Long hostId;
    //监控间隔 以秒为单位
    int rate;
    //监控指令
    String cmd;
    //监控类型 物理机监控 cpu 11 mem 12 ssh任务日志监控 21
    int type;
    //监控状态 0-创建，1-开始监控，2-停止监控，7-删除
    int status;

    public MonitorJob() {

    }
    public MonitorJob(int type) {
        this.type = type;
    }

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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonitorJob{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", rate=" + rate +
                ", cmd='" + cmd + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
