package com.liug.dao;

import com.liug.model.entity.MonitorJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/7/4.
 */
@Repository
@Mapper
public interface MonitorJobMapper {

    //查询全部
    List<MonitorJob> selectAll();
    //查询某个监控
    List<MonitorJob> selectJob(@Param("hostId") Long hostId, @Param("type") Integer type);



}
