package com.kilogate.framework.helper;

import com.kilogate.framework.annotation.Inject;
import com.kilogate.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * @author fengquanwei
 * @create 2017/11/17 19:36
 **/
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey(); // bean 类
                Object beanInstance = entry.getValue(); // bean 实例

                // 获取 bean 类的所有成员变量（bean field）
                Field[] fields = beanClass.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field beanField : fields) {
                        // 如果 bean beanField 被 Inject 注解标注，则通过反射初始化 bean beanField 的值
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
