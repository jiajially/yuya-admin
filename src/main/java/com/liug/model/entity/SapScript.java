package com.liug.model.entity;

import java.util.Date;

public class SapScript {
    // id :
    private Long id;
    // 描述 、 简介
    String  summary;
    String host;
    int instanceno;
    String sid;
    String router;
    int client;

    String username;
    String password;
    String language;
    String tcode;
    int timeout;

    String title;
    String formate;
    String createBy;

    // create_time :创建时间
    private Date createTime;

    // update_time :更新时间
    private Date updateTime;
    // status :状态
    private int status;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getInstanceno() {
        return instanceno;
    }

    public void setInstanceno(int instanceno) {
        this.instanceno = instanceno;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SapScript{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", host='" + host + '\'' +
                ", instanceno=" + instanceno +
                ", sid='" + sid + '\'' +
                ", router='" + router + '\'' +
                ", client=" + client +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", language='" + language + '\'' +
                ", tcode='" + tcode + '\'' +
                ", timeout=" + timeout +
                ", title='" + title + '\'' +
                ", formate='" + formate + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
