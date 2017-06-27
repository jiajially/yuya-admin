package com.liug.white.service;

import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SysRole;

/**
 * @Author liug
 * @Date 2016/10/14/14:53
 * @Description
 */
public interface SysRoleService {
    boolean isExsitRoleName(String name);

    long insertRole(SysRole sysRole, String permissionIds);

    void updateRole(SysRole sysRole, String permissionIds);

    boolean isExsitRoleNameExcludeId(long id, String name);

    SysRole selectById(long id);

    PageInfo selectPage(int page, int row);

    void deleteRole(SysRole sysRole);
}
