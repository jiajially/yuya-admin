package com.liug.model.dto;


import com.liug.model.entity.SysOrganization;
import com.liug.model.entity.SysRole;

import java.io.Serializable;

/**
 * @Author liug
 * @Date 2016/10/8/13:57
 * @Description
 */
public class UserRoleOriganization implements Serializable {
    private SysRole role;
    private SysOrganization organization;

    public UserRoleOriganization(SysRole role, SysOrganization organization) {
        this.role = role;
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "UserRoleOriganization{" +
                "role=" + role +
                ", organization=" + organization +
                '}';
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(SysOrganization organization) {
        this.organization = organization;
    }
}
