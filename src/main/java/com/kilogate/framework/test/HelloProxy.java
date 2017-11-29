package com.kilogate.framework.test;

import com.kilogate.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * HelloProxy
 *
 * @author fengquanwei
 * @create 2017/11/29 16:04
 **/
public class HelloProxy extends AspectProxy {
    @Override
    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable {
        System.out.println("hello before");
    }

    @Override
    public void after(Class<?> clazz, Method method, Object[] params, Object result) throws Throwable {
        System.out.println("hello after");
    }
}
