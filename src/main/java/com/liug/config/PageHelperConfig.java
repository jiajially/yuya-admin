package com.liug.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by wl on 2017/6/30.
 * 注册MyBatis分页插件PageHelper
 */

@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //4.0.0以后版本可以不设置该参数
        p.setProperty("dialect", "mysql");
        //该参数默认为false,设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        //和startPage中的pageNum效果一样
        p.setProperty("offsetAsPageNum", "true");
        //该参数默认为false 设置为true时，使用RowBounds分页会进行count查询
        p.setProperty("rowBoundsWithCount", "true");
        //设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        //相当于没有执行分页查询，但是返回结果仍然是Page类型
        p.setProperty("pageSizeZero", "false");
        //3.3.0 版本可用 - 分页参数合理化，默认false禁用
        //启用合理化时，如果pageNum< 1 会查询第一页，如果pageNum > pages会查询最后一页
        //禁用合理化时，如果pageNum< 1 或pageNum > pages会返回空数据
        p.setProperty("reasonable", "true");
        //3.5 .0 版本可用 - 为了支持startPage(Object params) 方法
        //增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值
        //可以配置pageNum, pageSize, count, pageSizeZero, reasonable, orderBy, 不配置映射的用默认值
        //不理解该含义的前提下，不要随便复制该配置
        p.setProperty("params", "pageNum=start;pageSize=limit;");
        //支持通过Mapper接口参数来传递分页参数
        p.setProperty("supportMethodsArguments", "true");
        //always总是返回PageInfo类型, check检查返回类型是否为PageInfo, none返回Page
        p.setProperty("returnPageInfo", "check");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
