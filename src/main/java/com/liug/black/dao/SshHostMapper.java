package com.liug.black.dao;

import com.liug.black.model.entity.SshHost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface SshHostMapper {
    //查询全部
    public List<SshHost> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("host") String host, @Param("username") String hostUser, @Param("envPath") String envPath);

    //查询数量
    public int selectCounts();

    //查询已存在数量
    public int selectCountsExists(@Param("host") String host,@Param("port") Integer port,@Param("username") String username);

    //新增
    public Long insert(SshHost sshTask);

    //更新
    public Long update(SshHost sshTask);

    //删除
    public Long deleteById(@Param("id") Long id);

    //通过id进行查询
    public SshHost selectById(@Param("id") Long id);
}
