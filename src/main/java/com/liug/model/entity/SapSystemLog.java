
package com.liug.model.entity;
import java.util.Date;
public class SapSystemLog {
Long id;
Long sid;
Integer operation;
Long operator;
String content;
Date createTime;
Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SapSystemLog{" +
                "id=" + id +
                ", sid=" + sid +
                ", operation=" + operation +
                ", operator=" + operator +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}