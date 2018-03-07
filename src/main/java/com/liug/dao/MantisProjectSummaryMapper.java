
package com.liug.dao;

import com.liug.model.entity.MantisProjectSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface MantisProjectSummaryMapper {
//查询全部
    List<MantisProjectSummary> selectAll();
//通过id进行查询
    MantisProjectSummary selectById(@Param("id") Long id);
}