package com.liug.dao;

import com.liug.model.entity.SysPermissionGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionGroupMapper {
    //新增
    Long insert(SysPermissionGroup SysPermissionGroup);

    //更新
    void update(SysPermissionGroup SysPermissionGroup);

    //通过对象进行查询
    SysPermissionGroup select(SysPermissionGroup SysPermissionGroup);

    //通过id进行查询
    SysPermissionGroup selectById(@Param("id") Long id);

    //查询全部
    List<SysPermissionGroup> selectAll();

    //查询数量
    int selectCounts();

    boolean isExistGroupName(@Param("name") String name);
}
