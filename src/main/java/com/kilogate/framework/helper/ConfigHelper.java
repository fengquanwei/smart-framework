package com.kilogate.framework.helper;

import com.kilogate.framework.constant.ConfigConstant;
import com.kilogate.framework.util.PropertiesUtil;

import java.util.Properties;

/**
 * 配置助手
 *
 * @author fengquanwei
 * @create 2017/11/12 20:21
 **/
public class ConfigHelper {
    private static final Properties CONFIG_PROPERTIES = PropertiesUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    public static String getAppAssetPath() {
        return PropertiesUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}
