package com.liug.black.dao;

import com.liug.black.model.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysLogMapper {
    //新增
    public Long insert(SysLog SysLog);

    //更新
    public void update(SysLog SysLog);

    //通过对象进行查询
    public SysLog select(SysLog SysLog);

    //通过id进行查询
    public SysLog selectById(@Param("id") Long id);

    //查询全部
    public List<SysLog> selectAll();

    //查询数量
    public int selectCounts();

    List<SysLog> selectLog(@Param("sort") String sort, @Param("order") String order, @Param("method") String method, @Param("url") String url, @Param("param") String param, @Param("result") String result);

}
