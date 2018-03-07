
package com.liug.model.entity;
import java.util.Date;
public class SapSystem {
Long id;
String sid;
String company;
String grouping;
String address;
Date createTime;
Long createBy;
Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SapSystem{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", company='" + company + '\'' +
                ", grouping='" + grouping + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", status=" + status +
                '}';
    }
}