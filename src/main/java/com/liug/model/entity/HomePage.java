package com.liug.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/7/29.
 */
public class HomePage {
    //条目id
    long id;
    //标题
    String title;
    //描述
    String description;
    //展示结果的单位
    String unit;
    //执行结果
    String result;
    //最新更新时间
    Date updateTime;
    //page页类别
    String homepage;
    //执行指令的主机ID
    long hostId;
    //执行指令
    String cmd;
    //状态 0-创建，1-活动状态
    int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date upadteTime) {
        this.updateTime = upadteTime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HomePage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", result='" + result + '\'' +
                ", updateTime=" + updateTime +
                ", homepage='" + homepage + '\'' +
                ", hostId=" + hostId +
                ", cmd='" + cmd + '\'' +
                ", status=" + status +
                '}';
    }
}
