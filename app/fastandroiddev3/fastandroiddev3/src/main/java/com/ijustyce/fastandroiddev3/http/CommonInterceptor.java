package com.ijustyce.fastandroiddev3.http;

import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by yangchun on 2016/11/10.
 */

class CommonInterceptor implements Interceptor {

    private HashMap<String, String> commonParameter = new HashMap<>();
    private HashMap<String, String> header = new HashMap<>();
    public CommonInterceptor() {

    }

    public CommonInterceptor addCommonHeader(String key, String value) {
        if (StringUtils.isEmpty(key)) return this;
        if (value != null) {
            header.put(key, value);
        }else {
            header.remove(key);
        }
        return this;
    }

    public CommonInterceptor addCommonParameter(String key, String value) {
        if (StringUtils.isEmpty(key)) return this;
        if (value != null) {
            commonParameter.put(key, value);
        }else {
            commonParameter.remove(key);
        }
        return this;
    }

    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!"POST".equalsIgnoreCase(request.method())) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        String subType = mediaType == null ? null : mediaType.subtype();
        //  有参数,并且类型不是 x-www-form-urlencoded 这里必须要判断是否为空,比如没有参数的post请求
        if (subType != null && !subType.equalsIgnoreCase("x-www-form-urlencoded")) {
            return false;
        }
        return true;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final Buffer buffer = new Buffer();
            if(request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public Response intercept(Interceptor.Chain chain) {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        //  针对POST请求
        if (canInjectIntoBody(oldRequest)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            String postBodyString = bodyToString(oldRequest.body());
            HashMap<String, String> map = StringUtils.getKeyValue(postBodyString);
            for(Map.Entry<String, String> entry : commonParameter.entrySet()) {
                if (entry == null) continue;
                String key = entry.getKey();
                if (!map.containsKey(key)) {
                    formBodyBuilder.add(key, entry.getValue());
                }
            }

            RequestBody formBody = formBodyBuilder.build();
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
            builder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
        }

        //  如果不是POST请求
        else if (!"POST".equalsIgnoreCase(oldRequest.method())){
            String postBodyString = bodyToString(oldRequest.body());
            HashMap<String, String> map = StringUtils.getKeyValue(postBodyString);
            for (Map.Entry<String, String> entry : commonParameter.entrySet()) {
                if (entry == null) continue;
                String key = entry.getKey();
                if (!map.containsKey(key)) {
                    authorizedUrlBuilder.addQueryParameter(key, entry.getValue());
                }
            }
        }

        for (Map.Entry<String, String> entry : header.entrySet()) {
            if (entry == null) continue;
            String key = entry.getKey();
            builder.addHeader(key, entry.getValue());
        }

        // 新的请求
        Request newRequest = builder.build();
        try {
            return chain.proceed(newRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new Response.Builder().request(newRequest).protocol(Protocol.HTTP_1_1)
                .code(HttpURLConnection.HTTP_UNAVAILABLE).build();
    }
}
