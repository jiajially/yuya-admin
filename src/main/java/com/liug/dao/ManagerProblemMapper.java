package com.liug.dao;

import com.liug.model.entity.ManagerProblem;
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
public interface ManagerProblemMapper {
    //查询全部
    List<ManagerProblem> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("begin") Date begin, @Param("end") Date end);

    //查询数量
    int selectCounts();

    //新增
    Long insert(ManagerProblem managerProblem);

    //更新
    Long update(ManagerProblem managerProblem);

    //删除
    Long deleteById(@Param("id") Long id);

    //通过id进行查询
    ManagerProblem selectById(@Param("id") Long id);



}
