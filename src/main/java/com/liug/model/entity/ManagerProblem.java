package com.liug.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/6/19.
 * `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `summary` varchar(500) DEFAULT NULL COMMENT '简介',
 `date` datetime DEFAULT current_timestamp COMMENT '上报日期',
 `channel` varchar(500) DEFAULT NULL COMMENT '上报渠道',
 `informer` varchar(500) DEFAULT NULL COMMENT '上报人',
 `finishdate` datetime DEFAULT null COMMENT '完成日期',
 `status` tinyint(4) DEFAULT '0' COMMENT '处理状态',

 `detail` varchar(500) DEFAULT NULL COMMENT '问题描述',
 `type` varchar(100) DEFAULT NULL COMMENT '问题性质',
 `level` varchar(100) DEFAULT NULL COMMENT '优先级',
 `system` varchar(500) DEFAULT NULL COMMENT '问题系统',

 `needtime` varchar(500) DEFAULT NULL COMMENT '所需时间',
 `needresources` varchar(500) DEFAULT NULL COMMENT '所需资源',
 `result` varchar(100) DEFAULT NULL COMMENT '处理结果',
 `dealer` varchar(100) DEFAULT NULL COMMENT '处理人员',
 `recorder` varchar(100) DEFAULT NULL COMMENT '记录人员',

 */
public class ManagerProblem {

    // id :
    private Long id;
    // 描述 、 简介
    String  summary;
    Date date;
    String channel;
    String informer;
    Date finishdate;
    int status;
    String detail;
    String type;
    String level;
    String system;
    String needtime;
    String needresources;
    String result;
    String dealer;
    String recorder;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) {
        this.informer = informer;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getNeedtime() {
        return needtime;
    }

    public void setNeedtime(String needtime) {
        this.needtime = needtime;
    }

    public String getNeedresources() {
        return needresources;
    }

    public void setNeedresources(String needresources) {
        this.needresources = needresources;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    @Override
    public String toString() {
        return "ManagerProblem{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", date=" + date +
                ", channel='" + channel + '\'' +
                ", informer='" + informer + '\'' +
                ", finishdate=" + finishdate +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", system='" + system + '\'' +
                ", needtime='" + needtime + '\'' +
                ", needresources='" + needresources + '\'' +
                ", result='" + result + '\'' +
                ", dealer='" + dealer + '\'' +
                ", recorder='" + recorder + '\'' +
                '}';
    }
}
