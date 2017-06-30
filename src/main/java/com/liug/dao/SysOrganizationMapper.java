package com.liug.dao;

import com.liug.model.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysOrganizationMapper {
    //新增
    Long insert(SysOrganization SysOrganization);

    //更新
    void update(SysOrganization SysOrganization);

    //通过对象进行查询
    SysOrganization select(SysOrganization SysOrganization);

    //通过id进行查询
    SysOrganization selectById(@Param("id") Long id);

    //查询全部
    List<SysOrganization> selectAll();

    //查询数量
    int selectCounts();

    List<SysOrganization> selectChildren(@Param("parentId") long parentId);

    boolean isExistFullName(@Param("fullName") String fullName);

    boolean isExistFullNameExcludeId(@Param("id") long id, @Param("fullName") String fullName);
}
