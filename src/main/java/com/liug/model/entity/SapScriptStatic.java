
package com.liug.model.entity;

import java.util.Date;

public class SapScriptStatic {
    Long id;
    String summary;
    String type;
    String code;
    String content;
    Date createTime;
    Date uodateTime;
    Integer createBy;
    Integer status;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getUodateTime() {
        return uodateTime;
    }

    public void setUodateTime(Date uodateTime) {
        this.uodateTime = uodateTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
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
        return "SapScriptStatic{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", uodateTime=" + uodateTime +
                ", createBy=" + createBy +
                ", status=" + status +
                '}';
    }
}