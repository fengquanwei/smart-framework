package com.kilogate.framework.bean;

import java.util.Map;

/**
 * 请求参数
 *
 * @author fengquanwei
 * @create 2017/11/17 20:39
 **/
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }

    /**
     * 根据参数名获取 String 类型参数值
     */
    public String getString(String name) {
        return (String) paramMap.get(name);
    }

    /**
     * 根据参数名获取 Integer 类型参数值
     */
    public Integer getInteger(String name) {
        return (Integer) paramMap.get(name);
    }
}
