package com.liug.dao;


import com.liug.model.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRolePermissionMapper {
    //新增
    Long insert(SysRolePermission SysRolePermission);

    //更新
    void update(SysRolePermission SysRolePermission);

    //通过对象进行查询
    SysRolePermission select(SysRolePermission SysRolePermission);

    //通过id进行查询
    SysRolePermission selectById(@Param("id") Long id);

    //查询全部
    List<SysRolePermission> selectAll();

    //查询数量
    int selectCounts();

    void deleteByRoleId(@Param("roleId") Long roleId);

    List<SysRolePermission> selectByRoleId(@Param("roleId") Long roleId);
}
