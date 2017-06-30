package com.liug.dao;

import com.liug.model.entity.SysUserRoleOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserRoleOrganizationMapper {
    //新增
    Long insert(SysUserRoleOrganization SysUserRoleOrganization);

    //更新
    void update(SysUserRoleOrganization SysUserRoleOrganization);

    //通过对象进行查询
    SysUserRoleOrganization select(SysUserRoleOrganization SysUserRoleOrganization);

    //通过id进行查询
    SysUserRoleOrganization selectById(@Param("id") Long id);

    //查询全部
    List<SysUserRoleOrganization> selectAll();

    //查询数量
    int selectCounts();

    void deleteUserId(@Param("userId") long userId);

    List<SysUserRoleOrganization> selectByUserId(@Param("userId") Long userId);

    List<Long> selectByRoleOrganizationId(@Param("roleOrganizationId") long roleOrganizationId);
}
