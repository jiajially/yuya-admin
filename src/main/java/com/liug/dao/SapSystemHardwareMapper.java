
package com.liug.dao;

import com.liug.model.entity.SapSystemHardware;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface SapSystemHardwareMapper {
//查询全部
    List<SapSystemHardware> selectAll();
//通过id进行查询
    SapSystemHardware selectById(@Param("id") Long id);
//新增
    Long insert(SapSystemHardware model);
//修改
    Long update(SapSystemHardware model);
//通过id删除
    Long deleteById(@Param("id") Long id);
}