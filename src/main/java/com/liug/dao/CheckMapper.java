package com.liug.dao;

import com.liug.model.entity.Check;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface CheckMapper {
    //查询全部
    List<Check> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("type") String type, @Param("begin") Date begin, @Param("end") Date end);

    //查询数量
    int selectCounts(@Param("type") String type, @Param("begin") Date begin, @Param("end") Date end);

    //通过id进行查询
    Check selectById(@Param("id") Long id);

    //新增
    Long insert(Check check);
}
