
package com.liug.dao;

import com.liug.model.entity.SapScriptStatic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SapScriptStaticMapper {
    //查询全部
    List<SapScriptStatic> selectAll();

    //通过id进行查询
    SapScriptStatic selectById(@Param("id") Long id);

    //通过code进行查询
    SapScriptStatic selectByCode(@Param("code") String code);

    //新增
    Long insert(SapScriptStatic model);

    //修改
    Long update(SapScriptStatic model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}