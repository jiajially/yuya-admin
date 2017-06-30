package com.liug.dao;

import com.liug.model.entity.SshScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface SshScriptMapper {
    //查询全部
    List<SshScript> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("name") String name, @Param("hostname") String hostname, @Param("cmd") String cmd);

    //查询数量
    int selectCounts();

    //新增
    Long insert(SshScript SshScript);

    //通过id进行查询
    SshScript selectById(@Param("id") Long id);

    //通过id删除
    Long deleteById(@Param("id") Long id);

    //更新End_time到最新--停止调度
    Long updateEndtime2CurrentById(@Param("id") Long id);

}
