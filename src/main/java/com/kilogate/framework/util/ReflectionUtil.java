package com.kilogate.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射工具类
 *
 * @author fengquanwei
 * @create 2017/11/13 14:55
 **/
public class ReflectionUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> clazz) {
        Object instance = null;

        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("创建实例异常, clazz: {}", clazz, e);
        } catch (IllegalAccessException e) {
            logger.error("创建实例异常, clazz: {}", clazz, e);
        }

        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;

        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            logger.error("调用方法异常, obj: {}, method: {}, args: {}", obj, method, Arrays.toString(args), e);
        } catch (InvocationTargetException e) {
            logger.error("调用方法异常, obj: {}, method: {}, args: {}", obj, method, Arrays.toString(args), e);
        }

        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("设置成员变量的值异常, obj: {}, field: {}, value: {}", obj, field, value, e);
        }
    }
}
