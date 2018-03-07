
package com.liug.dao;

import com.liug.model.entity.SapSystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SapSystemLogMapper {
    //查询全部
    List<SapSystemLog> selectAll();

    //通过id进行查询
    SapSystemLog selectById(@Param("id") Long id);

    //
    List<SapSystemLog> selectBySapsystemId(@Param("sapsystemId") Long sapsystemId);
    //新增
    Long insert(SapSystemLog model);

    //修改
    Long update(SapSystemLog model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}