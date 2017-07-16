package com.liug.dao;

import com.liug.model.entity.SshTaskLogSum;
import com.liug.model.entity.SshScript;
import com.liug.model.entity.SshTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface SshTaskMapper {
    //查询全部
    List<SshTask> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("name") String name, @Param("hostname") String hostname, @Param("cmd") String cmd);

    //查询数量
    int selectCounts();

    //新增
    Long insert(SshTask sshTask);

    //通过id进行查询
    SshTask selectById(@Param("id") Long id);

    Long updateSshResult(@Param("id") Long id, @Param("status") Integer status, @Param("result") String result);

    //查询全部
    List<SshScript> selectSshTask(@Param("sort") String sort, @Param("order") String order);

    //查询d待执行任务数量
    int selectSshTaskCounts();

    List<SshTaskLogSum> selectLogSumByHostID(@Param("hostId")Long hostId);
}
