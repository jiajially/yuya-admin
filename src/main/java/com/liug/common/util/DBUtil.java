package com.liug.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 此工具单独运行，用于创建数据库访问单元
 * 数据库访问分以下几个步骤
 * 1、数据库的实体类的创建，用于对应数据库的表结构
 * 2、数据库mybatis XML文件创建，用于定义增删改查的方法
 * 3、Dao层中的Mapper创建，用于对应XML文件定义的方法
 */

public class DBUtil {

    static String createXml(List<Model> modelList, String table, String table_db) {

        String content = "";
        content += "\n" + "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n";
        content += "\n" + "<mapper namespace=\"com.liug.dao." + table + "Mapper\">";
        content += "\n" + "<resultMap id=\"baseResultMap\" type=\"com.liug.model.entity." + table + "\">";
        for (Model m : modelList) {
            content += "\n" + "<result column=\"" + m.column_db + "\" property=\"" + m.column + "\" jdbcType=\"" + m.dbType + "\"/>";
        }
        content += "\n" + "</resultMap>";

        content += "\n" + "<sql id=\"sql_column\">";
        for (Model m : modelList) {
            content += "\n" + m.column_db;
        }
        content += "\n" + "</sql>";

        content += "\n" + "<sql id=\"sql_column_where\">";

        for (Model m : modelList) {
            String tmp = "";
            if (m.type.equals("String")) tmp = "and " + m.column + ".length() != 0";
            content += "\n" + " <if test=\"" + m.column + " !=null " + tmp + "\">\n" +
                    "            AND " + m.column_db + " = " + m.column_xml + "\n" +
                    "        </if>";
        }
        content += "\n" + "</sql>";

        //insert
        content += "\n" + "<insert id=\"insert\" parameterType=\"com.liug.model.entity." + table + "\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" +
                "        insert into " + table_db + "\n" +
                "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">";
        for (Model m : modelList) {
            if (!m.column.equals("id")) {
                String tmp = "";
                if (m.type.equals("String")) tmp = "and " + m.column + ".length() != 0";
                content += "\n" + " <if test=\"" + m.column + " !=null " + tmp + "\">\n" +
                        "            " + m.column_db + ",\n" +
                        "        </if>";
            }
        }

        content += "\n" + "</trim>\n" +
                "        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">";
        for (Model m : modelList) {
            if (!m.column.equals("id")) {
                String tmp = "";
                if (m.type.equals("String")) tmp = "and " + m.column + ".length() != 0";
                content += "\n" + " <if test=\"" + m.column + " !=null " + tmp + "\">\n" +
                        "            " + m.column_xml + ",\n" +
                        "        </if>";
            }
        }
        content += "\n" + " </trim>\n" +
                "    </insert>";

        //update
        content += "\n" + "<update id=\"update\" parameterType=\"com.liug.model.entity." + table + "\">\n" +
                "        update " + table_db + "\n" +
                "        <set>";
        for (Model m : modelList) {
            if (!m.column.equals("id")) {
                if (m.column.equals("updateTime")) {
                    String tmp = "";
                    if (m.type.equals("String")) tmp = "and " + m.column + ".length() != 0";

                    content += "\n" + " <if test=\"" + m.column + " !=null " + tmp + "\">\n" +
                            "            " + m.column_db + " = current_timestamp," + "\n" +
                            "        </if>";
                } else {
                    String tmp = "";
                    if (m.type.equals("String")) tmp = "and " + m.column + ".length() != 0";

                    content += "\n" + " <if test=\"" + m.column + " !=null " + tmp + "\">\n" +
                            "            " + m.column_db + " = " + m.column_xml + "," + "\n" +
                            "        </if>";
                }
            }
        }
        content += "\n" + "</set>\n" +
                "        where id =#{id} and status !=7\n" +
                "    </update>";
        //delete
        content += "\n" + "<update id=\"deleteById\">\n" +
                "        UPDATE " + table_db + "\n" +
                "        SET status = 7\n" +
                "        WHERE id = #{id}\n" +
                "    </update>";
        //select by id
        content += "\n" + "<select id=\"selectById\" parameterType=\"long\" resultType=\"com.liug.model.entity." + table + "\">\n" +
                "        SELECT *\n" +
                "        FROM " + table_db + "\n" +
                "        WHERE id = #{id} AND status != 7\n" +
                "    </select>";

        //select all
        content += "\n" + "<select id=\"selectAll\" parameterType=\"long\" resultType=\"com.liug.model.entity." + table + "\">\n" +
                "        SELECT *\n" +
                "        FROM " + table_db + "\n" +
                "        WHERE status != 7\n" +
                "    </select>";

        content += "\n" + "</mapper>";

        return content;

    }


    static String createModel(List<Model> modelList, String table) {
        String content = "";
        content += "\n" + "package com.liug.model.entity;";
        content += "\n" + "import java.util.Date;";
        content += "\n" + "public class " + table + " {";
        for (Model m : modelList) {
            content += "\n" + m.type + " " + m.column + ";";
        }
        content += "\n" + "}";
        return content;
    }


    public static void main() {
        List<Model> modelList = new ArrayList<Model>();
        /*
        * CREATE TABLE `custom_report_script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` bigint(20) not null,
  `type` tinyint(4) DEFAULT '0',
  `orderno` tinyint(4) DEFAULT '0',
  `title` varchar(500) DEFAULT NULL,
  `script_content` text DEFAULT NULL,
  `fomattor` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

        *
        *
        * */

        Long id;
        Long sid;
        Integer operation;
        Long operator;
        String content;
        Date create_time;
        Integer status;
        modelList.add(new Model("id", "id", "#{id}", "Long", "BIGINT", 20));
        modelList.add(new Model("sid", "sid", "#{sid}", "Long", "BIGINT", 20));
        modelList.add(new Model("operation", "operation", "#{operation}", "Integer", "TINYINT", 4));
        modelList.add(new Model("operator", "operator", "#{operator}", "Long", "BIGINT", 20));
        modelList.add(new Model("content", "content", "#{content}", "String", "VARCHAR", 5000));
        modelList.add(new Model("createTime", "create_time", "#{createTime}", "Date", "TIMESTAMP", 500));
        modelList.add(new Model("status", "status", "#{status}", "Integer", "TINYINT", 4));



        String table = "SapSystemLog";
        String table_db = "sap_system_log";
        String root = FileUtil.getProjectPath();
        FileUtil.createFileForce(root + "/src/main/java/com/liug/model/entity/" + table + ".java", createModel(modelList, table));
        FileUtil.createFileForce(root + "/src/main/resources/mybatis/" + table + ".xml", createXml(modelList, table, table_db));
        FileUtil.createFileForce(root + "/src/main/java/com/liug/dao/" + table + "Mapper.java", createMapper(table));

    }


    static String createMapper(String table) {
        String content = "";
        content += "\n" + "package com.liug.dao;\n" +
                "\n" +
                "import com.liug.model.entity." + table + ";\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "import org.apache.ibatis.annotations.Param;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "import java.util.List;";
        content += "\n" + "@Repository\n" +
                "@Mapper\n" +
                "public interface " + table + "Mapper {";
        content += "\n" + "//查询全部\n" +
                "    List<" + table + "> selectAll();";
        content += "\n" + "//通过id进行查询\n" +
                "    " + table + " selectById(@Param(\"id\") Long id);";
        content += "\n" + "//新增\n" +
                "    Long insert(" + table + " model);";
        content += "\n" + "//修改\n" +
                "    Long update(" + table + " model);";
        content += "\n" + "//通过id删除\n" +
                "    Long deleteById(@Param(\"id\") Long id);";
        content += "\n" + "}";
        return content;

    }

}

class Model {
    String column;
    String column_db;
    String column_xml;
    String type;
    String dbType;
    Integer length;

    public Model(String c, String c_d, String c_x, String t, String t_d, int l) {
        column = c;
        column_db = c_d;
        column_xml = c_x;
        type = t;
        dbType = t_d;
        length = l;
    }


    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumn_db() {
        return column_db;
    }

    public void setColumn_db(String column_db) {
        this.column_db = column_db;
    }

    public String getColumn_xml() {
        return column_xml;
    }

    public void setColumn_xml(String column_xml) {
        this.column_xml = column_xml;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
