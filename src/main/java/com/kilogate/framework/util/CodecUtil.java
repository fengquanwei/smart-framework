package com.kilogate.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 *
 * @author fengquanwei
 * @create 2017/11/17 21:19
 **/
public class CodecUtil {
    /**
     * URL 编码
     */
    public static String encodeURL(String source) {
        String target = null;

        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return target;
    }

    /**
     * URL 解码
     */
    public static String decodeURL(String source) {
        String target = null;

        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return target;
    }
}
