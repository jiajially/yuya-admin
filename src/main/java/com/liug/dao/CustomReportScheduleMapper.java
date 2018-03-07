
package com.liug.dao;

import com.liug.model.entity.CustomReportSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CustomReportScheduleMapper {
    //查询全部
    List<CustomReportSchedule> selectAll();

    List<CustomReportSchedule> selectByReportId(@Param("reportId") Long reportId);

    List<CustomReportSchedule> selectBySid(@Param("sid") Long sid);

    //通过id进行查询
    CustomReportSchedule selectById(@Param("id") Long id);

    //新增
    Long insert(CustomReportSchedule model);

    //修改
    Long update(CustomReportSchedule model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}