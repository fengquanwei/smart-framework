package com.fengquanwei.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库助手
 *
 * @author fengquanwei
 * @create 2017/12/5 15:40
 **/
public class DatabaseHelper {
    private static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    // 数据库配置
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/demo";
    private static final String username = "root";
    private static final String password = "kilogate";

    // 数据库连接
    private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("开启事务异常", e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                logger.error("提交事务异常", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                logger.error("回滚事务异常", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    // TODO CONNECTION_HOLDER 需要明确用途

    /**
     * 获取数据库连接
     */
    private static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        try {
            if (connection == null) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException e) {
            logger.error("获取数据库连接", e);
        } catch (SQLException e) {
            logger.error("获取数据库连接", e);
        } finally {
            CONNECTION_HOLDER.set(connection);
        }

        return connection;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("关闭数据库连接异常", e);
        } finally {
            CONNECTION_HOLDER.remove();
        }
    }
}
