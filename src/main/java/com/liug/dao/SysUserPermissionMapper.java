package com.liug.dao;

import com.liug.model.entity.SysUserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserPermissionMapper {
    //新增
     Long insert(SysUserPermission SysUserPermission);

    //更新
     void update(SysUserPermission SysUserPermission);

    //通过对象进行查询
     SysUserPermission select(SysUserPermission SysUserPermission);

    //通过id进行查询
     SysUserPermission selectById(@Param("id") Long id);

    //查询全部
     List<SysUserPermission> selectAll();

    //查询数量
     int selectCounts();

    void deleteByUserId(@Param("userId") long userId);

    List<SysUserPermission> selectByUserId(Long userId);
}
