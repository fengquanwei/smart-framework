package com.kilogate.framework.helper;

import com.kilogate.framework.annotation.Action;
import com.kilogate.framework.bean.Handler;
import com.kilogate.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 *
 * @author fengquanwei
 * @create 2017/11/17 19:58
 **/
public class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (controllerClassSet != null && controllerClassSet.size() > 0) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) { // 验证 URL 映射规则
                                String[] configs = mapping.split(":");
                                if (configs != null && configs.length == 2) {
                                    String requestMethod = configs[0];
                                    String requestPath = configs[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
