
package com.liug.dao;

import com.liug.model.entity.SapSystemHost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SapSystemHostMapper {
    //查询全部
    List<SapSystemHost> selectAll();

    //通过id进行查询
    SapSystemHost selectById(@Param("id") Long id);

    //新增
    Long insert(SapSystemHost model);

    //修改
    Long update(SapSystemHost model);

    //通过id删除
    Long deleteById(@Param("id") Long id);


    //通过id进行查询
    List<SapSystemHost> selectBySapsystemId(@Param("sapsystemId") Long sapsystemId);
}