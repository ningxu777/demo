package com.neil.demo.jooq;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Neil on 16/4/8.
 * @Description BoneCP连接池工具类,管理数据库连接
 * @Date 16/4/8
 */
public class BoneCPUtil {

    private static BoneCP connectionPool = null;
    private static BoneCPConfig config = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf8");
            config.setUsername("root");
            config.setPassword("");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接池
    public static BoneCP getConnectionPool(){
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionPool;
    }
    //获取连接
    public static Connection getConnection(BoneCP connectionPool){
        Connection connection = null;
        if(connectionPool != null){
            try {
                connection = connectionPool.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    //关闭连接池
    public static void closeConnectionPool(BoneCP connectionPool){
        connectionPool.close();
    }
    //关闭连接
    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //??????
    public static DSLContext getContext(Connection connection){
        return DSL.using(connection);
    }
}
