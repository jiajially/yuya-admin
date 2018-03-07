
package com.liug.dao;

import com.liug.model.entity.MantisUserTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface MantisUserTableMapper {
    //查询全部
    List<MantisUserTable> selectAll();

    //通过id进行查询
    MantisUserTable selectById(@Param("id") Long id);

    MantisUserTable selectByUsername(@Param("username") String username);

    //新增
    Long insert(MantisUserTable model);

    //修改
    Long update(MantisUserTable model);

    //通过id删除
    Long deleteById(@Param("id") Long id);
}