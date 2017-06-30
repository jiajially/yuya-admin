package com.liug.dao;

import com.liug.model.entity.SysDataItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysDataItemMapper {
    //新增
    Long insert(SysDataItem SysDataItem);

    //更新
    void update(SysDataItem SysDataItem);

    //通过对象进行查询
    SysDataItem select(SysDataItem SysDataItem);

    //通过id进行查询
    SysDataItem selectById(@Param("id") Long id);

    //查询全部
    List<SysDataItem> selectAll();

    //查询数量
    int selectCounts();

    boolean isExistName(@Param("key") String key, @Param("groupId") long groupId);

    void deleteById(@Param("id") Long id);

    boolean isExistDataItemNameExcludeId(@Param("id") Long id, @Param("key") String key, @Param("groupId") long groupId);

    String selectByKey(@Param("key") String key, @Param("groupId") Long groupId);
}
