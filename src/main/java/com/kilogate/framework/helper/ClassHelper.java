package com.kilogate.framework.helper;

import com.kilogate.framework.annotation.Controller;
import com.kilogate.framework.annotation.Service;
import com.kilogate.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Class 助手
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
        return getClassSetByAnnotation(Service.class);
    }

    /**
     * 获取应用包名下的所有 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getClassSetByAnnotation(Controller.class);
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

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(superClass) && !superClass.equals(clazz)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

}