
package com.liug.dao;

import com.liug.model.entity.CustomReportResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CustomReportResultMapper {
    //查询全部
    List<CustomReportResult> selectAll();

    //查询等待执行列表
    List<CustomReportResult> selectProcessList();

    //通过id进行查询
    CustomReportResult selectById(@Param("id") Long id);

    //通过id进行查询
    List<CustomReportResult> selectByScheduleId(@Param("scheduleId") Long scheduleId);


    //新增
    Long insert(CustomReportResult model);

    //修改
    Long update(CustomReportResult model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}