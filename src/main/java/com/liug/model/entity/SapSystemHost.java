
package com.liug.model.entity;
import java.util.Date;
public class SapSystemHost {
Long id;
String hostname;
Long hostId;
String hardware;
Long hardwareId;
String sapsystem;
Long sapsystemId;
String cpu;
String mem;
String disk;
String dbinfo;
String dbsize;
String sapproduct;
String kernel;
String sapcomponents;
Date installTime;
Date createTime;
Date updateTime;
String contact;
Integer type;
Integer status;
Long creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getSapsystem() {
        return sapsystem;
    }

    public void setSapsystem(String sapsystem) {
        this.sapsystem = sapsystem;
    }

    public Long getSapsystemId() {
        return sapsystemId;
    }

    public void setSapsystemId(Long sapsystemId) {
        this.sapsystemId = sapsystemId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getDbinfo() {
        return dbinfo;
    }

    public void setDbinfo(String dbinfo) {
        this.dbinfo = dbinfo;
    }

    public String getDbsize() {
        return dbsize;
    }

    public void setDbsize(String dbsize) {
        this.dbsize = dbsize;
    }

    public String getSapproduct() {
        return sapproduct;
    }

    public void setSapproduct(String sapproduct) {
        this.sapproduct = sapproduct;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getSapcomponents() {
        return sapcomponents;
    }

    public void setSapcomponents(String sapcomponents) {
        this.sapcomponents = sapcomponents;
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "SapSystemHost{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", hostId=" + hostId +
                ", hardware='" + hardware + '\'' +
                ", hardwareId=" + hardwareId +
                ", sapsystem='" + sapsystem + '\'' +
                ", sapsystemId=" + sapsystemId +
                ", cpu='" + cpu + '\'' +
                ", mem='" + mem + '\'' +
                ", disk='" + disk + '\'' +
                ", dbinfo='" + dbinfo + '\'' +
                ", dbsize='" + dbsize + '\'' +
                ", sapproduct='" + sapproduct + '\'' +
                ", kernel='" + kernel + '\'' +
                ", sapcomponents='" + sapcomponents + '\'' +
                ", installTime=" + installTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", contact='" + contact + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", creator=" + creator +
                '}';
    }
}