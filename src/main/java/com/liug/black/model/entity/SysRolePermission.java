package com.liug.black.model.entity;

/**
 * @Author: liug
 * @Date: 2016-10-12 14:21
 * @Description:
 */
public class SysRolePermission {

    // id :
    private Long id;

    // sys_permission_id :权限id
    private Long sysPermissionId;

    // sys_role_id :角色id
    private Long sysRoleId;

    // rank :排序
    private Long rank;

    // create_time :创建时间
    private java.util.Date createTime;

    // update_time :更新时间
    private java.util.Date updateTime;

    // create_by :创建人id
    private Long createBy;

    // update_by :更新人id
    private Long updateBy;

    // status :数据状态,1:正常,2:删除
    private Integer status;

    /**
     * get
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * set
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get 权限id
     *
     * @return Long
     */
    public Long getSysPermissionId() {
        return sysPermissionId;
    }

    /**
     * set 权限id
     *
     * @param sysPermissionId
     */
    public void setSysPermissionId(Long sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }

    /**
     * get 角色id
     *
     * @return Long
     */
    public Long getSysRoleId() {
        return sysRoleId;
    }

    /**
     * set 角色id
     *
     * @param sysRoleId
     */
    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * get 排序
     *
     * @return Long
     */
    public Long getRank() {
        return rank;
    }

    /**
     * set 排序
     *
     * @param rank
     */
    public void setRank(Long rank) {
        this.rank = rank;
    }

    /**
     * get 创建时间
     *
     * @return java.util.Date
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * set 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get 更新时间
     *
     * @return java.util.Date
     */
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    /**
     * set 更新时间
     *
     * @param updateTime
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * get 创建人id
     *
     * @return Long
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * set 创建人id
     *
     * @param createBy
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * get 更新人id
     *
     * @return Long
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * set 更新人id
     *
     * @param updateBy
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * get 数据状态,1:正常,2:删除
     *
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * set 数据状态,1:正常,2:删除
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysRolePermission{" +
                "id='" + id + '\'' +
                ", sysPermissionId='" + sysPermissionId + '\'' +
                ", sysRoleId='" + sysRoleId + '\'' +
                ", rank='" + rank + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", status=" + status +
                '}';
    }
}
