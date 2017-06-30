package com.liug.service;



import com.liug.model.dto.PageInfo;
import com.liug.model.dto.SysOrganizationTree;
import com.liug.model.entity.SysOrganization;

import java.util.List;

/**
 * @Author: liug
 * @Date : 2016/10/10
 */
public interface SysOrganizationService {
    Long insertOrganization(SysOrganization sysOrganization);

    int deleteOrganization(long id);

    void updateOrganization(SysOrganization organization);

    PageInfo selectPage(int page, int row, long id);

    public SysOrganizationTree selectSysOrganizationTree(long id);

    public List<SysOrganizationTree> selectChildrenTreeList(long id);

    boolean isExistFullName(String fullName);

    SysOrganization selectOrganization(long id);

    boolean isExistFullNameExcludeId(long id, String fullName);

}
