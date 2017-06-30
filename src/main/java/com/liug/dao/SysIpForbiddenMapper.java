package com.liug.dao;


import com.liug.model.entity.SysIpForbidden;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysIpForbiddenMapper {
    //新增
    Long insert(SysIpForbidden SysIpForbidden);

    //更新
    void update(SysIpForbidden SysIpForbidden);

    //通过对象进行查询
    SysIpForbidden select(SysIpForbidden SysIpForbidden);

    //通过id进行查询
    SysIpForbidden selectById(@Param("id") Long id);

    //查询全部
    List<SysIpForbidden> selectAll();

    //查询数量
    int selectCounts();

    void deleteById(@Param("id") long id);

    boolean isExistIp(String ip);

    boolean isExistIpExcludeId(@Param("ip") String ip, @Param("id") long id);
}
