package com.liug.model.entity;


/**
 * Created by liugang on 2017/7/31.
 */
public class SshHostDetail {
    Integer osId;
    Integer swId;
    String osName;
    String swName;
    String osVersion;
    String swVersion;

    public Integer getOsId() {
        return osId;
    }

    public void setOsId(Integer osId) {
        this.osId = osId;
    }

    public Integer getSwId() {
        return swId;
    }

    public void setSwId(Integer swId) {
        this.swId = swId;
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

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getSwName() {
        return swName;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    @Override
    public String toString() {
        return "SshHostDetail{" +
                "osId=" + osId +
                ", swId=" + swId +
                ", osName='" + osName + '\'' +
                ", swName='" + swName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", swVersion='" + swVersion + '\'' +
                '}';
    }
}
