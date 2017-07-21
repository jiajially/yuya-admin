package com.liug.model.entity;

/**
 * Created by liugang on 2017/7/14.
 */
public class SshTaskLogSum {
    int cnt;
    long hostId;
    String dateRef;
    int status;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public String getDateRef() {
        return dateRef;
    }

    public void setDateRef(String dateRef) {
        this.dateRef = dateRef;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SshTaskLogSum{" +
                "cnt=" + cnt +
                ", hostId=" + hostId +
                ", dateRef='" + dateRef + '\'' +
                ", status=" + status +
                '}';
    }
}
