package com.liug.black.dao;

import com.liug.black.model.entity.SysPermissionGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionGroupMapper {
    //新增
    public Long insert(SysPermissionGroup SysPermissionGroup);

    //更新
    public void update(SysPermissionGroup SysPermissionGroup);

    //通过对象进行查询
    public SysPermissionGroup select(SysPermissionGroup SysPermissionGroup);

    //通过id进行查询
    public SysPermissionGroup selectById(@Param("id") Long id);

    //查询全部
    public List<SysPermissionGroup> selectAll();

    //查询数量
    public int selectCounts();

    boolean isExistGroupName(@Param("name") String name);
}
