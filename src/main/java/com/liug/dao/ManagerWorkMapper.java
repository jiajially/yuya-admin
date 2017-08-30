package com.liug.dao;

import com.liug.model.entity.ManagerWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liugang on 2017/5/30.
 */
@Repository
@Mapper
public interface ManagerWorkMapper {
    //查询全部
    List<ManagerWork> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("begin") Date begin, @Param("end") Date end);

    //查询数量
    int selectCounts();

    //新增
    Long insert(ManagerWork managerWork);

    //更新
    Long update(ManagerWork managerWork);

    //删除
    Long deleteById(@Param("id") Long id);

    //通过id进行查询
    ManagerWork selectById(@Param("id") Long id);



}
