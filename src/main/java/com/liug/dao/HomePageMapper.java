package com.liug.dao;

import com.liug.model.entity.HomePage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liugang on 2017/7/4.
 */
@Repository
@Mapper
public interface HomePageMapper {

    //查询某个类别的首页配置
    List<HomePage> selectByHomePage(@Param("homepage") String homepage);

    //查询类别
    List<HomePage> selectHomePage();

    //查询全部
    List<HomePage> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("homepage") String homepage);

    //查询数量
    int selectCounts();

    //新增
    Long insert(HomePage homePage);

    //更新
    Long update(HomePage homePage);

    //删除
    Long deleteById(@Param("id") Long id);

    //通过id进行查询
    HomePage selectById(@Param("id") Long id);
}
