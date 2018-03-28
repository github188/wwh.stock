package com.ijustyce.fastandroiddev3.http;

import com.ijustyce.fastandroiddev3.baseLib.utils.RegularUtils;

import java.util.HashMap;

/**
 * Created by yangchun on 2017/2/27.
 */

public class HttpParameter {

    String url;
    private static String baseUrl;
    private HashMap<String, String> parameter, header, cookie;
    private static HashMap<String, String> commonParameter = new HashMap<>();
    private static HashMap<String, String> commonHeader = new HashMap<>();
    private static HashMap<String, String> commonCookie = new HashMap<>();

    public static void setBaseUrl(String url) {
        if (!RegularUtils.isCommonUrl(url)) return;
        baseUrl = url;
    }

    private HttpParameter(){
        parameter = new HashMap<>();
        header = new HashMap<>();
        cookie = new HashMap<>();
    }

    /**
     * 设置请求的路劲，可以是相对路径也可以是全路劲。
     * @return
     */
    public static HttpParameter url(String url) {
        if (!RegularUtils.isCommonUrl(baseUrl)) {
            throw new IllegalArgumentException("please set right baseUrl");
        }
        HttpParameter parameter = new HttpParameter();
        parameter.url = baseUrl + url;
        return parameter;
    }

    public HttpParameter add(String key, String value) {
        if (value == null) {
            parameter.remove(key);
        } else {
            parameter.put(key, value);
        }
        return this;
    }

    public HttpParameter header(String key, String value) {
        if (value == null) {
            header.remove(key);
        } else {
            header.put(key, value);
        }
        return this;
    }

    public HttpParameter cookie(String key, String value) {
        if (value == null) {
            cookie.remove(key);
        } else {
            cookie.put(key, value);
        }
        return this;
    }

    HashMap<String, String> getHeader() {
        if (!commonCookie.isEmpty()) {
            cookie.putAll(commonCookie);
        }
        if (!commonHeader.isEmpty()) {
            header.putAll(commonHeader);
        }
        if (cookie.isEmpty()) return header;
        StringBuilder stringBuilder = new StringBuilder();
        for (HashMap.Entry<String, String> entry : cookie.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        header.put("Cookie", stringBuilder.toString());
        return header;
    }

    HashMap<String, String> getParameter() {
        if (!commonParameter.isEmpty()) {
            parameter.putAll(commonParameter);
        }
        return parameter;
    }

    /**
     *  以下是通用参数
     */

    public static void addCommonParameter(String key, String value) {
        if (value == null) {
            commonParameter.remove(key);
        } else {
            commonParameter.put(key, value);
        }
    }

    public static void addCommonHeader(String key, String value) {
        if (value == null) {
            commonHeader.remove(key);
        } else {
            commonHeader.put(key, value);
        }
    }

    public static void addCommonCookie(String key, String value) {
        if (value == null) {
            commonCookie.remove(key);
        } else {
            commonCookie.put(key, value);
        }
    }
}
