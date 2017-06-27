package com.liug.black.model.entity;

import java.util.Date;

/**
 * Created by liugang on 2017/6/20.
 */
public class SshScript {
    // id :
    private Long id;
    // id :
    private Long taskId;

    // name :
    private String name;
    // host :
    private String host;

    // host_id :主机id
    private Long hostId;

    // cmd :指令
    private String cmd;

    // create_time :创建时间
    private Date createTime;

    // start_time :计划开始时间
    private Date startTime;

    // end_time :计划结束时间
    private Date endTime;

    //rate 时钟周期
    private Integer rate;

    // create_by :创建人
    private Long createBy;

    // status :指令状态,1:可执行,2:不可执行
    private Integer status;


    private String hostname;

    private String username;

    private String password;

    private Integer port;

    private String envPath;

    SshHost  sshHost;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getEnvPath() {
        return envPath;
    }

    public void setEnvPath(String envPath) {
        this.envPath = envPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
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

    public SshHost getSshHost() {
        sshHost = new SshHost();
        sshHost.setUsername(this.username);
        sshHost.setPassword(this.password);
        sshHost.setHost(this.host);
        sshHost.setPort(this.port);
        sshHost.setEnvPath(this.envPath);
        return sshHost;
    }

    @Override
    public String toString() {
        return "SshScript{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", hostId=" + hostId +
                ", cmd='" + cmd + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rate=" + rate +
                ", createBy=" + createBy +
                ", status=" + status +
                ", hostname='" + hostname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", envPath='" + envPath + '\'' +
                '}';
    }
}
