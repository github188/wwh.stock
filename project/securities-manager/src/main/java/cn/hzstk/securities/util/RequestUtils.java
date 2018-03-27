package cn.hzstk.securities.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http Client工具类
 * 
 * @author Kevin Huang
 * @since 0.0.1 2015年2月10日 下午2:04:24
 */
public class RequestUtils {
    private static final int timeout = 60000;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static CloseableHttpClient client;

    // 设置请求超时时间， DEFAULT_CHARSET。可以后写到配置文件
    private final static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();

    /**
     * 初始化httpClient, SSLContext信任所有
     * 
     * @return CloseableHttpClient
     */
    @Deprecated
    private static synchronized CloseableHttpClient getClient() {
        if (null == client) {
            SSLConnectionSocketFactory sslCSF = null;

            try {
                // 信任所有
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
                sslCSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client = HttpClients.custom().setSSLSocketFactory(sslCSF).build();
        }
        return client;
    }

    /**
     * get请求
     * 
     * @param url
     * @param params 参数
     * @return 请求结果
     */
    public static String doGet(String url, Map<String, Object> params) {
        URI uri = generateURL(url, params);
        HttpGet get = new HttpGet(uri);
        get.setConfig(requestConfig);

        String res = execute(get);
        System.out.println("request: " + url + " response :" + res);
        return res;
    }
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * get请求
     * 
     * @param url
     * @param params url后参数
     * @param jsonStr request body对象 (可为 null)
     * @return 请求结果
     */
    public static String doPost(String url, Map<String, Object> params, String jsonStr) {
        URI uri = generateURL(url, params);
        HttpPost post = new HttpPost(uri);
        post.setConfig(requestConfig);

        if (null != jsonStr) {
            System.out.println("request: " + url + " params :" + jsonStr);
//            HttpEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            HttpEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_FORM_URLENCODED);
            post.setEntity(entity);
        }

        String res = execute(post);
        System.out.println("request: " + url + " response :" + res);
        return res;
    }

    public static String doPost(String url, String jsonStr) {
        return doPost(url, null, jsonStr);
    }

    public static String doPost(String url, Map<String, Object> forms) {
        StringBuilder builder = new StringBuilder();
        for (Entry<String, Object> entry : forms.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        String buildStr = builder.toString();
        String postStr = buildStr.substring(0, buildStr.length()-1);
        return doPost(url, null, postStr);
    }
    
    /**
     * 发起请求，关闭资源
     * 
     * @param request （HttpPost 或 HttpGet）
     * @return response 请求返回值
     */
    private static String execute(HttpUriRequest request) {
        String responseStr = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = getClient().execute(request);
            responseStr = EntityUtils.toString(httpResponse.getEntity(), DEFAULT_CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseStr;
    }

    /**
     * 生成请求的url
     * 
     * @param url 不带参数的url字符串
     * @param params 参数
     * @return URI， 请求的uri对象
     */
    private static URI generateURL(String url, Map<String, Object> params) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }
    
}