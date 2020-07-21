package com.auto.utils;

import io.qameta.allure.Step;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLUtils {

//    public static void main(String[] args) {
//        String sql = "select count(*) from member where mobile_phone = '15619227530'";
//        String sql1 = "select leave_amount from member where id = 77";
//        Object query = query(sql);
//        //使用count(*)默认的类型为long 可以用query.getClass()查看类型
//        System.out.println(query);
//    }

    /**
     * 调用DBUtils执行对应的查询sql语句，并返回查询的结果
     * @param sql
     * @return
     */
    @Step("执行sql {sql}")
    public static Object query(String sql){
        if (StringUtils.isBlank(sql)){
            return null;
        }
        //创建QueryRunner对象
        QueryRunner qr = new QueryRunner();
        //获取数据库连接
        Connection conn = JDBCUtils.getConnection();
        try {
            //创建返回结果类型对象
            ScalarHandler rsh = new ScalarHandler();
            //执行sql查询语句
            Object result = qr.query(conn, sql, rsh);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据库连接
            JDBCUtils.close(conn);
        }
        return null;
    }
}
