package com.liug.dao;

import com.liug.model.entity.SysRoleOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleOrganizationMapper {
    //新增
    Long insert(SysRoleOrganization SysRoleOrganization);

    //更新
    void update(SysRoleOrganization SysRoleOrganization);

    //通过对象进行查询
    SysRoleOrganization select(SysRoleOrganization SysRoleOrganization);

    //通过id进行查询
    SysRoleOrganization selectById(@Param("id") Long id);

    //查询全部
    List<SysRoleOrganization> selectAll();

    //查询数量
    int selectCounts();

    boolean isExistName(@Param("name") String name, @Param("parentId") long parentId);

    boolean isExistNameExcludeId(@Param("id") long id, @Param("name") String name, @Param("parentId") long parentId);

    List<SysRoleOrganization> selectChildren(@Param("parentId") long parentId);

    List<Long> selectByRoleId(@Param("roleId") long roleId);
}
