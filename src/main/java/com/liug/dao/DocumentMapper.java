package com.liug.dao;

import com.liug.model.entity.Document;
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
public interface DocumentMapper {
    //查询全部
    List<Document> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("type") String type, @Param("begin") Date begin, @Param("end") Date end);

    //查询数量
    int selectCounts(@Param("type") String type, @Param("begin") Date begin, @Param("end") Date end);

    //通过id进行查询
    List<Document> selectBySid(@Param("sid") Long sid);

    //通过id进行查询
    Document selectById(@Param("id") Long id);

    //通过id进行删除
    long deleteById(@Param("id") Long id);

    //通过id进行删除link
    long deleteDocumentSid(@Param("documentId") Long id,@Param("sid") Long sid);

    //新增
    Long insert(Document check);

    //新增Sid连接
    Long insertDocumentSid(@Param("documentId")Long documentId,@Param("sid")Long sid);

    //查询数据库文件数量
    int fileCount(@Param("type") String type, @Param("summary") String summary);

}
