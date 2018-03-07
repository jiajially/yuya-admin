package com.liug.model.json;

import com.liug.model.entity.SapSystem;

public class Sid {
    long id;
    String company;
    String group;
    String sid;
    String address;

    public Sid() {

    }

    public Sid(SapSystem sapSystem) {
        this.id = sapSystem.getId();
        this.company = sapSystem.getCompany();
        this.group = sapSystem.getGrouping();
        this.address = sapSystem.getAddress();
        this.sid = sapSystem.getSid();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
