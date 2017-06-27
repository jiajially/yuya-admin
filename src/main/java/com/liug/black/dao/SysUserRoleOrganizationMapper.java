package com.liug.black.dao;

import com.liug.black.model.entity.SysUserRoleOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserRoleOrganizationMapper {
    //新增
    public Long insert(SysUserRoleOrganization SysUserRoleOrganization);

    //更新
    public void update(SysUserRoleOrganization SysUserRoleOrganization);

    //通过对象进行查询
    public SysUserRoleOrganization select(SysUserRoleOrganization SysUserRoleOrganization);

    //通过id进行查询
    public SysUserRoleOrganization selectById(@Param("id") Long id);

    //查询全部
    public List<SysUserRoleOrganization> selectAll();

    //查询数量
    public int selectCounts();

    void deleteUserId(@Param("userId") long userId);

    List<SysUserRoleOrganization> selectByUserId(@Param("userId") Long userId);

    List<Long> selectByRoleOrganizationId(@Param("roleOrganizationId") long roleOrganizationId);
}
