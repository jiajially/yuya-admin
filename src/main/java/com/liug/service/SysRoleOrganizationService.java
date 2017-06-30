package com.liug.service;


import com.liug.model.dto.PageInfo;
import com.liug.model.dto.SysRoleOrganizationTree;
import com.liug.model.entity.SysRoleOrganization;

import java.util.List;

/**
 * @Author liug
 * @Date 2016/10/17/16:30
 * @Description
 */
public interface SysRoleOrganizationService {
    boolean isExistName(String name, long parentId);

    long insertRoleOrganization(SysRoleOrganization roleOrganization);

    boolean isExistNameExcludeId(long id, String name, long parentId);

    void updateRoleOrganization(SysRoleOrganization roleOrganization);

    SysRoleOrganization selectRoleOrganizationById(long id);

    PageInfo selectPage(int page, int rows, long id);

    public SysRoleOrganizationTree selectSysRoleOrganizationTree(long id);

    public List<SysRoleOrganizationTree> selectSysRoleOrganizationTreeChildrenList(long id);
}
