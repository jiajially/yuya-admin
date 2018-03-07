
package com.liug.dao;

import com.liug.model.entity.CustomReportScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CustomReportScriptMapper {
    //查询全部
    List<CustomReportScript> selectAll();

    List<CustomReportScript> selectByReportId(@Param("reportId") Long reportId);

    //通过id进行查询
    CustomReportScript selectById(@Param("id") Long id);

    //新增
    Long insert(CustomReportScript model);

    //修改
    Long update(CustomReportScript model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}