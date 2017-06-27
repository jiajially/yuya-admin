package com.liug.black.dao;


import com.liug.black.model.entity.SysDataGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysDataGroupMapper {
    //新增
    public Long insert(SysDataGroup SysDataGroup);

    //更新
    public void update(SysDataGroup SysDataGroup);

    //通过对象进行查询
    public SysDataGroup select(SysDataGroup SysDataGroup);

    //通过id进行查询
    public SysDataGroup selectById(@Param("id") Long id);

    //查询全部
    public List<SysDataGroup> selectAll();

    //查询数量
    public int selectCounts();

    boolean isExistName(@Param("name") String name);
}
