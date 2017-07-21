package com.liug.dao;

import com.liug.model.entity.MonitorLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/7/4.
 */
@Repository
@Mapper
public interface MonitorLogMapper {

    //新增
    Long insert(MonitorLog monitorLog);

    //获取监控数据
    List<MonitorLog> selectByJobId(@Param("jobId")Long jobId, @Param("deadLine")Date deadLine);

}