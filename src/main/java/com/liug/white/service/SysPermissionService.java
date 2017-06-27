package com.liug.white.service;


import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.entity.SysPermission;
import com.liug.black.model.entity.SysPermissionGroup;

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
