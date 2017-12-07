package com.fengquanwei.framework.helper;

import com.fengquanwei.framework.annotation.Inject;
import com.fengquanwei.framework.util.ArrayUtil;
import com.fengquanwei.framework.util.CollectionUtil;
import com.fengquanwei.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * @author fengquanwei
 * @create 2017/11/17 19:36
 **/
public final class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey(); // bean 类
                Object beanInstance = beanEntry.getValue(); // bean 实例
                // 获取 bean 类的所有成员变量（bean field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 如果 bean 类的 beanField 被 Inject 注解标注，则通过反射初始化 bean 类的 beanField 的值
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
