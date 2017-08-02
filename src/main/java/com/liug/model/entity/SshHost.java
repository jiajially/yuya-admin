package com.liug.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/6/19.
 */
public class SshHost {

    // id :
    private Long id;
    // 描述 、 简介
    String  summary;
    String host;
    int port;
    String username;
    String password;
    String envPath;
    boolean isValid;
    boolean isEnable;

    //操作系统id
    long osId;
    //软件id
    long swId;

    //操作系统
    String os;
    //软件
    String sw;
    //操作系统版本
    String osVersion;
    //软件版本
    String swVersion;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getEnvPath() {
        return envPath;
    }

    public void setEnvPath(String envPath) {
        this.envPath = envPath;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
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

    public long getOsId() {
        return osId;
    }

    public void setOsId(long osId) {
        this.osId = osId;
    }

    public long getSwId() {
        return swId;
    }

    public void setSwId(long swId) {
        this.swId = swId;
    }


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSw() {
        return sw;
    }

    public void setSw(String sw) {
        this.sw = sw;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    @Override
    public String toString() {
        return "SshHost{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", envPath='" + envPath + '\'' +
                ", isValid=" + isValid +
                ", isEnable=" + isEnable +
                ", osId=" + osId +
                ", swId=" + swId +
                ", os='" + os + '\'' +
                ", sw='" + sw + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", swVersion='" + swVersion + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
