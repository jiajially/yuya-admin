package com.liug.model.entity;


import java.util.Date;

/**
 * Created by liugang on 2017/8/24.
 */
public class Document {
    Long id;
    String summary;
    String type;
    String suffix;
    String url;
    Date createtime;
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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", type='" + type + '\'' +
                ", suffix='" + suffix + '\'' +
                ", url='" + url + '\'' +
                ", createtime=" + createtime +
                ", status=" + status +
                '}';
    }
}
