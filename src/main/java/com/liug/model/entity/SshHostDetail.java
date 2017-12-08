package com.liug.model.entity;


/**
 * Created by liugang on 2017/7/31.
 */
public class SshHostDetail {
    Long osId;
    Long swId;
    String osName;
    String swName;
    String osVersion;
    String swVersion;

    public SshHostDetail(){}

    public SshHostDetail(SshHost sshHost){
        osId=sshHost.getOsId();
        swId=sshHost.getSwId();
        osName=sshHost.getOs();
        swName=sshHost.getSw();
        osVersion=sshHost.getOsVersion();
        swVersion=sshHost.getSwVersion();
    }

    public Long getOsId() {
        return osId;
    }

    public void setOsId(Long osId) {
        this.osId = osId;
    }

    public Long getSwId() {
        return swId;
    }

    public void setSwId(Long swId) {
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
