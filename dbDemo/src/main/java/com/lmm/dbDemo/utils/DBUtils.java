package com.lmm.dbDemo.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库工具类，支持连接池
 * 
 * @author dongquan.yan
 *
 */
public class DBUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);
    private static DataSource dataSource;

    public static void initDataSource() {
        //读取配置文件
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(propertiesUtil.getValue("mysql.jdbc.driverClassName"));
        ds.setUsername(propertiesUtil.getValue("mysql.jdbc.username"));
        ds.setPassword(propertiesUtil.getValue("mysql.jdbc.password"));
        ds.setUrl(propertiesUtil.getValue("mysql.jdbc.url"));
        ds.setInitialSize(1); // 初始的连接数；
        ds.setMaxActive(20);
        ds.setMaxIdle(20);
        ds.setMaxWait(60000);
        dataSource = ds;
    }

    public static DataSource getdataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        Connection conn = null;
        if (dataSource == null) {
            initDataSource();
        }
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            initDataSource();
            throw new RuntimeException("获取数据库链接失败");
        }
        return conn;
    }

    public static void main(String[] args) throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getdataSource());
        MapListHandler rsh = new MapListHandler();
        List<Map<String, Object>> list = queryRunner.query("select * from user", rsh);
        System.out.println(list);
    }
}
