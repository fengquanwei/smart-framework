package com.kilogate.framework.helper;

import com.kilogate.framework.annotation.Controller;
import com.kilogate.framework.annotation.Service;
import com.kilogate.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 *
 * @author fengquanwei
 * @create 2017/11/13 14:23
 **/
public class ClassHelper {
    // 定义类集合（用于存放所加载的类）
    private static final Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有 Service 类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有 Bean 类（包括：Service，Controller 等）
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }
}