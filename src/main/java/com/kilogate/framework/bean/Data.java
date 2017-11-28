package com.kilogate.framework.bean;

/**
 * 数据（Action 返回值）
 *
 * @author fengquanwei
 * @create 2017/11/17 20:48
 **/
public class Data {
    private Object model; // 模型数据

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
