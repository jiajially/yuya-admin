package com.liug.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/8/21.
 */
public class Check {
    // id :
    private Long id;
    // 描述 、 简介
    String  summary;
    String  host;
    String  hostname;
    String type;
    String result;
    private Date exectime;
    int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getExectime() {
        return exectime;
    }

    public void setExectime(Date exectime) {
        this.exectime = exectime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", host='" + host + '\'' +
                ", hostname='" + hostname + '\'' +
                ", type='" + type + '\'' +
                ", result='" + result + '\'' +
                ", exectime=" + exectime +
                ", status=" + status +
                '}';
    }
}
