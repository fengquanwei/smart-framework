package com.kilogate.framework.test;

import com.kilogate.framework.proxy.Proxy;
import com.kilogate.framework.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Target
 *
 * @author fengquanwei
 * @create 2017/11/29 16:06
 **/
public class Target {
    void sayHi() {
        System.out.println("Hi");
    }

    public static void main(String[] args) {
        List<Proxy> proxyList = new ArrayList<>();
        proxyList.add(new HelloProxy());
        Target proxy = ProxyManager.createProxy(Target.class, proxyList);
        proxy.sayHi();
    }
}
