package com.liug.white.service.impl;

import com.liug.black.dao.SysPermissionMapper;
import com.liug.black.dao.SysRoleMapper;
import com.liug.black.dao.SysRolePermissionMapper;
import com.liug.black.model.dto.PageInfo;
import com.liug.black.model.dto.SysRoleDto;
import com.liug.black.model.entity.SysPermission;
import com.liug.black.model.entity.SysRole;
import com.liug.black.model.entity.SysRolePermission;
import com.liug.white.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liug
 * @Date 2016/10/14/14:53
 * @Description
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {
    private static Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public boolean isExsitRoleName(String name) {
        return sysRoleMapper.isExsitName(name);
    }

    @Override
    public long insertRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.insert(sysRole);
        String[] permissionIdsArray = permissionIds.split(",");
        for (int i = 0; i < permissionIdsArray.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Long.valueOf(permissionIdsArray[i]));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return sysRole.getId();
    }

    @Override
    public boolean isExsitRoleNameExcludeId(long id, String name) {
        return sysRoleMapper.isExsitNameExcludeId(id, name);
    }

    @Override
    public SysRole selectById(long id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public PageInfo selectPage(int page, int row) {
        int counts = sysRoleMapper.selectCounts();
        List<SysRole> sysRoles = sysRoleMapper.selectAll();

        List<SysRoleDto> sysRoleDtoList = new ArrayList<>();
        for (int i = 0; i < sysRoles.size(); i++) {
            SysRoleDto sysRoleDto = new SysRoleDto();
            BeanUtils.copyProperties(sysRoles.get(i), sysRoleDto);
            List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectByRoleId(sysRoles.get(i).getId());

            List<SysPermission> sysPermissionList = new ArrayList<>();
            for (int j = 0; j < sysRolePermissionList.size(); j++) {
                SysPermission sysPermission = sysPermissionMapper.selectById(sysRolePermissionList.get(j).getSysPermissionId());
                sysPermissionList.add(sysPermission);
            }
            sysRoleDto.setSysPermissions(sysPermissionList);
            sysRoleDtoList.add(sysRoleDto);
        }
        PageInfo pageInfo = new PageInfo(counts, sysRoleDtoList);
        return pageInfo;
    }

    @Override
    public void deleteRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
        sysRolePermissionMapper.deleteByRoleId(sysRole.getId());
    }

    @Override
    public void updateRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.update(sysRole);
        sysRolePermissionMapper.deleteByRoleId(sysRole.getId());
        String[] pIds = permissionIds.split(",");
        for (int i = 0; i < pIds.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Long.valueOf(pIds[i]));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
    }
}
