package com.fengquanwei.framework.helper;

import com.fengquanwei.framework.constant.ConfigConstant;
import com.fengquanwei.framework.util.PropertiesUtil;

import java.util.Properties;

/**
 * 配置助手类
 *
 * @author fengquanwei
 * @create 2017/11/12 20:21
 **/
public final class ConfigHelper {
    private static final Properties PROPERTIES = PropertiesUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * 获取 JDBC 驱动
     */
    public static String getJdbcDriver() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取 JDBC URL
     */
    public static String getJdbcUrl() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取 JDBC 用户名
     */
    public static String getJdbcUsername() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取 JDBC 密码
     */
    public static String getJdbcPassword() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用 JSP 路径
     */
    public static String getAppJspPath() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用静态资源路径
     */
    public static String getAppAssetPath() {
        return PropertiesUtil.getString(PROPERTIES, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    /**
     * 获取应用文件上传限制
     */
    public static int getAppUploadLimit() {
        return PropertiesUtil.getInt(PROPERTIES, ConfigConstant.APP_UPLOAD_LIMIT, 10);
    }

    /**
     * 根据属性名获取 String 类型的属性值
     */
    public static String getString(String key) {
        return PropertiesUtil.getString(PROPERTIES, key);
    }

    /**
     * 根据属性名获取 int 类型的属性值
     */
    public static int getInt(String key) {
        return PropertiesUtil.getInt(PROPERTIES, key);
    }

    /**
     * 根据属性名获取 boolean 类型的属性值
     */
    public static boolean getBoolean(String key) {
        return PropertiesUtil.getBoolean(PROPERTIES, key);
    }
}
