
package com.liug.dao;

import com.liug.model.entity.SapSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface SapSystemMapper {
//查询全部
    List<SapSystem> selectAll();
//通过id进行查询
    SapSystem selectById(@Param("id") Long id);
//新增
    Long insert(SapSystem model);
//修改
    Long update(SapSystem model);
//通过id删除
    Long deleteById(@Param("id") Long id);
}