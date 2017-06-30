package com.liug.service;


import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SysPermission;
import com.liug.model.entity.SysPermissionGroup;

import java.util.List;

/**
 * @Author: liug
 * @Date : 2016/10/15
 */
public interface SysPermissionService {
    boolean isExistName(long groupId, String name);

    boolean isExistCode(long groupId, String code);

    long insertPermission(SysPermission sysPermission);

    SysPermission selectById(long id);

    void update(SysPermission sysPermission);

    boolean isExistNameExcludeId(long id, long groupId, String name);

    boolean isExistCodeExcludeId(long id, long groupId, String code);

    PageInfo selectPage(int page, int rows);

    boolean isExistGroupName(String name);

    long insertPermissionGroup(SysPermissionGroup sysPermissionGroup);

    List<SysPermissionGroup> selectGroup();
}
