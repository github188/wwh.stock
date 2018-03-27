package com.skoo.stock.common.service;

import com.alibaba.fastjson.JSONObject;
import com.skoo.common.SystemConfig;
import com.skoo.commons.httpclient.HttpProtocolHandler;
import com.skoo.commons.httpclient.HttpRequest;
import com.skoo.commons.httpclient.HttpResponse;
import com.skoo.stock.api.domain.A;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用接口
 *
 * @author wangtao
 */
public class CommonService {

    private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);

    /**
     * 梦想通用接口
     *
     * @param parameterMap 请求参数
     * @param path         请求路径
     * @return Json字符串
     */
    public static String getManJSON(Map<Object, Object> parameterMap, String path) {
        Map<String, Object> result = new HashMap<>();
        try {
            String aPath = SystemConfig.INSTANCE.getValue("dreamApiUrl") + path;
            LOG.info("访问URL：" + aPath);
            return getJSON(parameterMap, aPath);
        } catch (Exception ex) {
            result.put(A.CODE, "-99");
            result.put(A.MSG, "接口调用异常，请检查接口路径");
            return JSONObject.toJSONString(result);
        }
    }

    /**
     * 百度API通用接口
     *
     * @param parameterMap 请求参数
     * @return Json字符串
     */
    public static String getBaiduJSON(Map<Object, Object> parameterMap) {
        Map<String, Object> result = new HashMap<>();
        try {
            String aPath = SystemConfig.INSTANCE.getValue("baidu.api.url");
            parameterMap.put("ak", SystemConfig.INSTANCE.getValue("baidu.api.ak"));
            LOG.info("访问URL：" + aPath);
            return getJSON(parameterMap, aPath);
        } catch (Exception ex) {
            result.put(A.CODE, "-99");
            result.put(A.MSG, "百度接口调用异常，请检查接口路径");
            return JSONObject.toJSONString(result);
        }
    }

    /**
     * 通用接口
     *
     * @param parameterMap 请求参数
     * @param url          请求URL
     * @return Json字符串
     */
    public static String getJSON(Map<Object, Object> parameterMap, String url) {
        if (url == null) {
            LOG.info("url不能为空");
            return null;
        }

        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setCharset("utf-8");

        if (parameterMap != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (Object parameterKey : parameterMap.keySet()) {
                Object parameterValue = parameterMap.get(parameterKey);
                if (parameterKey != null && parameterValue != null) {
                    nameValuePairList.add(new NameValuePair(parameterKey.toString(), parameterValue.toString()));
                }
            }
            NameValuePair[] parameters = nameValuePairList.toArray(new NameValuePair[nameValuePairList.size()]);
            request.setParameters(parameters);
        }

        HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
        HttpResponse response;
        try {
            response = handler.execute(request);
            return response.getStringResult();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
