package com.liug.dao;

import com.liug.model.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper {
    //新增
    Long insert(SysPermission SysPermission);

    //更新
    void update(SysPermission SysPermission);

    //通过对象进行查询
    SysPermission select(SysPermission SysPermission);

    //通过id进行查询
    SysPermission selectById(@Param("id") Long id);

    //查询全部
    List<SysPermission> selectAll();

    //查询数量
    int selectCounts();

    boolean isExistName(@Param("groupId") long groupId, @Param("name") String name);

    boolean isExistCode(@Param("groupId") long groupId, @Param("code") String code);

    boolean isExistNameExcludeId(@Param("id") long id, @Param("groupId") long groupId, @Param("name") String name);

    boolean isExistCodeExcludeId(@Param("id") long id, @Param("groupId") long groupId, @Param("code") String code);
}
