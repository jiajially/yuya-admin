package com.liug.dao;

import com.liug.model.entity.SysLoginStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysLoginStatusMapper {
    //新增
     Long insert(SysLoginStatus SysLoginStatus);

    //更新
     void update(SysLoginStatus SysLoginStatus);

    //通过对象进行查询
     SysLoginStatus select(SysLoginStatus SysLoginStatus);

    //通过id进行查询
     SysLoginStatus selectById(@Param("id") Long id);

    //查询全部
     List<SysLoginStatus> selectAll();

    //查询数量
     int selectCounts();

    SysLoginStatus selectByUserIdAndPlatform(@Param("userId") Long userId, @Param("platform") int platform);

    List<SysLoginStatus> selectByUserId(@Param("userId") long userId);
}
