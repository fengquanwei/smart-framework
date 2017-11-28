package com.kilogate.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON 工具类
 *
 * @author fengquanwei
 * @create 2017/11/17 21:22
 **/
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 POJO 转为 JSON
     */
    public static <T> String toJson(T obj) {
        String json = null;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 将 JSON 转为 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo = null;

        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pojo;
    }
}
