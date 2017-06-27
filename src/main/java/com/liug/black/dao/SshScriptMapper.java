package com.liug.black.dao;

import com.liug.black.model.entity.SshScript;
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
    public List<SshScript> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("name") String name,@Param("hostname") String hostname, @Param("cmd") String cmd);

    //查询数量
    public int selectCounts();

    //新增
    public Long insert(SshScript SshScript);

    //通过id进行查询
    public SshScript selectById(@Param("id") Long id);

    //通过id删除
    public Long deleteById(@Param("id") Long id);

    //更新End_time到最新--停止调度
    public Long updateEndtime2CurrentById(@Param("id") Long id);

}
