package com.kilogate.framework.bean;

import java.lang.reflect.Method;

/**
 * Action（请求处理器）
 *
 * @author fengquanwei
 * @create 2017/11/17 19:56
 **/
public class Handler {
    private Class<?> controllerClass; // Controller 类
    private Method actionMethod; // Action 方法

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
