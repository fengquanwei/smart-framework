package com.fengquanwei.framework;

import com.fengquanwei.framework.helper.*;
import com.fengquanwei.framework.util.ClassUtil;

/**
 * Helper 加载器
 *
 * @author fengquanwei
 * @create 2017/11/17 20:23
 **/
public class HelperLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class};

        for (Class<?> clazz : classes) {
            ClassUtil.loadClass(clazz.getName(), false);
        }
    }
}