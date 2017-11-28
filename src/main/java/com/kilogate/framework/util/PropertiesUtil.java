package com.kilogate.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具类
 *
 * @author fengquanwei
 * @create 2017/11/11 17:03
 **/
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = null;
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("加载属性文件异常, fileName: {}", fileName, e);
        }

        return properties;
    }

    /**
     * 获取属性值
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.contains(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }
}
