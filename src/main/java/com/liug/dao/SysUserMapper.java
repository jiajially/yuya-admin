package com.liug.dao;

import com.liug.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper {
    //新增
     Long insert(SysUser SysUser);

    //更新
     void update(SysUser SysUser);

    //通过对象进行查询
     SysUser select(SysUser SysUser);

    //通过id进行查询
     SysUser selectById(@Param("id") Long id);

    //查询全部
     List<SysUser> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("loginName") String loginName, @Param("zhName") String zhName, @Param("email") String email, @Param("phone") String phone, @Param("address") String address);

    //查询数量
     int selectCounts();

    boolean selectByLoginName(@Param("loginName") String loginName);

    void deleteById(@Param("id") long id);

    boolean isExistLoginNameExcludeId(@Param("id") long id, @Param("loginName") String loginName);

    SysUser selectUserByLoginName(@Param("loginName") String loginName);
}
