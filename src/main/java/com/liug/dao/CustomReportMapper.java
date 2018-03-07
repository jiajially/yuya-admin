
package com.liug.dao;

import com.liug.model.entity.CustomReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface CustomReportMapper {
//查询全部
    List<CustomReport> selectAll();
//通过id进行查询
    CustomReport selectById(@Param("id") Long id);
//新增
    Long insert(CustomReport model);
//修改
    Long update(CustomReport model);
//通过id删除
    Long deleteById(@Param("id") Long id);
}