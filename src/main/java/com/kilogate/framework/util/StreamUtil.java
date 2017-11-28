package com.kilogate.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 *
 * @author fengquanwei
 * @create 2017/11/17 21:16
 **/
public class StreamUtil {
    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
