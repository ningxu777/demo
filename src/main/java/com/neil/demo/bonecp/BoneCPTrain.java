package com.neil.demo.bonecp;

import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Neil on 16/4/8.
 *
 * @description 练习使用BoneCP数据库连接池
 */
public class BoneCPTrain {

    public static void main(String[] args) {
        com.jolbox.bonecp.BoneCP connectionPool = null;
        Connection connection = null;
        try {
            //加载mysql的jdbc驱动
            Class.forName("com.mysql.jdbc.Driver");
            //创建BoneCP连接池配置
            BoneCPConfig boneCPCongif = new BoneCPConfig();
            boneCPCongif.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf8");
            boneCPCongif.setUsername("root");
            boneCPCongif.setPassword("");
            //设置连接池的最小/最大连接数
            boneCPCongif.setMinConnectionsPerPartition(5);
            boneCPCongif.setMaxConnectionsPerPartition(10);
            //初始化连接池
            connectionPool = new com.jolbox.bonecp.BoneCP(boneCPCongif);
            //从连接池获取一个数据库连接
            connection = connectionPool.getConnection();

            if (connection != null) {
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery("select * FROM album");
                while (result.next()) {
                    System.out.println(result.getInt("id") + "---" + result.getString("name"));
                }
            }
            //关闭连接池
            connectionPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
