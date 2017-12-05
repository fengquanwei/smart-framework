package com.fengquanwei.framework.proxy;

/**
 * 代理
 *
 * @author fengquanwei
 * @create 2017/11/29 11:23
 **/
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}