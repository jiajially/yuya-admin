package com.liug.dao;


import com.liug.model.entity.SysDataGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysDataGroupMapper {
    //新增
    Long insert(SysDataGroup SysDataGroup);

    //更新
    void update(SysDataGroup SysDataGroup);

    //通过对象进行查询
    SysDataGroup select(SysDataGroup SysDataGroup);

    //通过id进行查询
    SysDataGroup selectById(@Param("id") Long id);

    //查询全部
    List<SysDataGroup> selectAll();

    //查询数量
    int selectCounts();

    boolean isExistName(@Param("name") String name);
}
