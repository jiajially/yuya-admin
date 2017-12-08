package com.liug.dao;

import com.liug.model.entity.SapScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface SapScriptMapper {
    //查询全部
    List<SapScript> selectAll();

    //新增
    Long insert(SapScript sapScript);

    //更新
    //Long update(SshHost sshTask);

    //删除
    Long deleteById(@Param("id") Long id);

    //通过id进行查询
    SapScript selectById(@Param("id") Long id);


}
