package com.kilogate.framework.helper;

import com.kilogate.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 *
 * @author fengquanwei
 * @create 2017/11/17 19:15
 **/
public class BeanHelper {
    // bean 容器
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取 Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return (T) BEAN_MAP.get(clazz);
    }
}
