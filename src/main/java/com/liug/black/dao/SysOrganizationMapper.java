package com.liug.black.dao;

import com.liug.black.model.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysOrganizationMapper {
    //新增
    public Long insert(SysOrganization SysOrganization);

    //更新
    public void update(SysOrganization SysOrganization);

    //通过对象进行查询
    public SysOrganization select(SysOrganization SysOrganization);

    //通过id进行查询
    public SysOrganization selectById(@Param("id") Long id);

    //查询全部
    public List<SysOrganization> selectAll();

    //查询数量
    public int selectCounts();

    List<SysOrganization> selectChildren(@Param("parentId") long parentId);

    boolean isExistFullName(@Param("fullName") String fullName);

    boolean isExistFullNameExcludeId(@Param("id") long id, @Param("fullName") String fullName);
}
