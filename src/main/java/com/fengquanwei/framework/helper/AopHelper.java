package com.fengquanwei.framework.helper;

import com.fengquanwei.framework.annotation.Aspect;
import com.fengquanwei.framework.annotation.Service;
import com.fengquanwei.framework.proxy.AspectProxy;
import com.fengquanwei.framework.proxy.Proxy;
import com.fengquanwei.framework.proxy.ProxyManager;
import com.fengquanwei.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手
 *
 * @author fengquanwei
 * @create 2017/11/29 21:28
 **/
public class AopHelper {
    private static Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            logger.error("AOP init failure", e);
        }
    }

    /**
     * 获取代理类与目标类集合之间的映射关系
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();

        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);

        return proxyMap;
    }

    /**
     * 创建目标类与代理类对象列表之间的映射关系
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();

                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }

        }

        return targetMap;
    }

    /**
     * 添加切面代理
     * 这里的代理类是指切面类，即扩展了 AspectProxy 抽象类的类，还要带有 Aspect 注解
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);

                Set<Class<?>> targetClassSet = new HashSet<>();
                Class<? extends Annotation> annotation = aspect.value();
                if (annotation != null && !annotation.equals(Aspect.class)) {
                    targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
                }

                proxyMap.put(proxyClass, targetClassSet);
            }
        }

    }

    /**
     * 添加事务代理
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, proxyClassSet);
    }
}
