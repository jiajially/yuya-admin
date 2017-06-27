package com.liug.black.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/5/30.
 */
public class SshTask {

    // id :
    private Long id;

    // name :
    private String name;

    // host :
    private String script_id;

    // cmd :指令
    private String cmd;

    // cmd :主机
    private String hostname;

    // mark :备注
    private String mark;

    // result :执行结果
    private String result;

    // create_time :创建时间
    private Date createTime;

    // create_time :调度时间
    private Date planTime;

    // update_time :执行时间
    private Date execTime;

    // create_by :创建人
    private Long createBy;

    // status :指令状态,1:执行成功,2:执行失败,3:未执行
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScript_id() {
        return script_id;
    }

    public void setScript_id(String script_id) {
        this.script_id = script_id;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
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
        return "SshTask{" +
                "id=" + id +
                ", script_id='" + script_id + '\'' +
                ", cmd='" + cmd + '\'' +
                ", hostname='" + hostname + '\'' +
                ", mark='" + mark + '\'' +
                ", result='" + result + '\'' +
                ", createTime=" + createTime +
                ", planTime=" + planTime +
                ", execTime=" + execTime +
                ", createBy=" + createBy +
                ", status=" + status +
                '}';
    }
}
