package com.neil.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * mysql连接工具类
 */
public class MysqlTool {
    private static String HOST = "127.0.0.1:3306";
    private static String NAME = "root";
    private static String PASSWD = "";
    private static String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getInstance() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8", NAME, PASSWD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;

    }
}
