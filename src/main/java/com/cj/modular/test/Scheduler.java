package com.cj.modular.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 *
 * </p>
 *
 * @author Caoj
 * @date 2022-06-08 16:27
 */
public class Scheduler {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 连接数据库
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql:///test?characterEncoding=utf-8", "root", "root");
        Statement statement = connection.createStatement();
        statement.execute("insert into model values(null, " + 1 + "," + 1 + ", now())");
        statement.close();
        connection.close();
    }
}
