package com.liug.dao;

import com.liug.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper {
    //新增
     Long insert(SysRole SysRole);

    //更新
     void update(SysRole SysRole);

    //通过对象进行查询
     SysRole select(SysRole SysRole);

    //通过id进行查询
     SysRole selectById(@Param("id") Long id);

    //查询全部
     List<SysRole> selectAll();

    //查询数量
     int selectCounts();

    boolean isExsitName(@Param("name") String name);

    boolean isExsitNameExcludeId(@Param("id") long id, @Param("name") String name);
}
