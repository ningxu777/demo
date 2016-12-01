package com.neil.demo.jooq;

import com.jolbox.bonecp.BoneCP;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;

/**
 * Created by Neil on 16/4/8.
 * 使用BoneCP数据库连接池管理数据库连接
 */
public class JooqDao {

    private static DSLContext dslContext = null;

    //获取DSLContext
    private static DSLContext getDSLContext() {
        BoneCP connectionPool = BoneCPUtil.getConnectionPool();
        Connection connection = BoneCPUtil.getConnection(connectionPool);
        dslContext = BoneCPUtil.getContext(connection);
        return dslContext;
    }

    //简单(条件)查询
    public static void select(String name) {
        DSLContext dslContext = getDSLContext();
        Table<Record> table = DSL.table("album"); //表名?
        SelectQuery<Record> selectQuery = dslContext.selectQuery(table); //获取查询对象
        Condition condition = DSL.field("name").eq(name.getBytes()); //设置查询条件
        selectQuery.addConditions(condition);//添加查询条件
        Result<Record> fetch = selectQuery.fetch();
        for (Object obj : fetch) {
            Record record = (Record) obj;

            System.out.println(String.valueOf(record.getValue("name")));
        }

    }

    //简单更新纪录
    public static void update(int id) {
        DSLContext dslContext = getDSLContext();
        Table<Record> table = DSL.table("album");
        UpdateQuery<Record> updateQuery = dslContext.updateQuery(table);
        updateQuery.addValue(DSL.field("name"), "数据结构_郝斌".getBytes());
        Condition condition = DSL.field("id").eq(id);
        updateQuery.addConditions(condition);
        int execute = updateQuery.execute();
        System.out.println(execute);
    }

    //原生态的sql查询
    public static void getVal() {
        DSLContext dslContext = getDSLContext();
        Table<Record> table = DSL.table("album");
        Result<Record> fetch = dslContext.select().from(table).where("ownerId = 7").and("id > 1").orderBy(DSL.field("id").desc()).fetch();
        for (Object obj : fetch) {
            Record record = (Record) obj;
            System.out.println(record.getValue("name") + "::::" + record.getValue("id"));
        }
    }

    //验证DSL.exits方法(判断是否存在某个字段)
    public static void exits() {
        DSLContext dslContext = getDSLContext();
        Table<Record> table = DSL.table("album");
        SelectQuery<Record> selectQuery = dslContext.selectQuery(table);
        Condition condition = DSL.exists(DSL.select(DSL.field("name")));
        selectQuery.addConditions(condition);
        Result<Record> fetch = selectQuery.fetch();
        for (Object obj : fetch) {
            Record record = (Record) obj;
            System.out.println(record.getValue("name"));
        }
    }


    public static void main(String[] args) {
        select("鸟哥的Linux私房菜");
        update(2);
        getVal();
        exits();
    }
}
