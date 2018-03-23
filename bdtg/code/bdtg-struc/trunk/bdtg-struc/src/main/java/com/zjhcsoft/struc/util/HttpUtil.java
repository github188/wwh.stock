package com.zjhcsoft.struc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 进行http访问的基本类
 * 
 * @author qiaoel@zjhcsoft.com
 * 
 */
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String METHOD_POST = "POST";

    private static final String METHOD_GET = "GET";

    private static final int CONNECTTIMEOUT = 5000;

    private static final int READTIMEOUT = 5000;

    private static class DefaultTrustManager implements X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] cert, String oauthType)
                throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] cert, String oauthType)
                throws java.security.cert.CertificateException {
        }
    }

    public static HttpURLConnection getConnection(URL url, String method, String ctype)
            throws IOException {

        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() },
                        new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", "hc-restclient-java-1.0");
        conn.setRequestProperty("Content-Type", ctype);
        conn.setRequestProperty("Connection", "Keep-Alive");
        return conn;

    }

    /**
     * 通过get方法访问，默认编码为utf-8
     * 
     * @param url 访问的url地址
     * @param params 请求需要的参数
     * @return 返回请求响应的数据
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 通过get方法访问
     * 
     * @param url 访问的url地址
     * @param params 请求需要的参数
     * @param charset 字符编码
     * @return 返回请求响应的数据
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params, String charset)
            throws IOException {
        if (StringUtil.isEmpty(url) || params == null) {
            return null;
        }
        String response = "";
        url += "?" + buildQuery(params, charset);
        HttpURLConnection conn = null;
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        conn = getConnection(new URL(url), METHOD_GET, ctype);
        response = getResponseAsString(conn);
        return response;
    }

    /**
     * 
     * @param url api请求的权路径url地址
     * @param params api请求的业务级参数
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, CONNECTTIMEOUT, READTIMEOUT);
    }

    /**
     * 
     * 通过post方法请求数据，默认字符编码为utf-8
     * 
     * @param url 请求的url地址
     * @param params 请求的参数
     * @param connectTimeOut 请求连接过期时间
     * @param readTimeOut 请求读取过期时间
     * @return 请求响应
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeOut,
            int readTimeOut) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeOut, readTimeOut);
    }

    /**
     * 
     * 通过post方法请求数据
     * 
     * @param url 请求的url地址
     * @param params 请求的参数
     * @param charset 字符编码格式
     * @param connectTimeOut 请求连接过期时间
     * @param readTimeOut 请求读取过期时间
     * @return 请求响应
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset,
            int connectTimeOut, int readTimeOut) throws IOException {
        HttpURLConnection conn = null;
        String response = "";
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        conn = getConnection(new URL(url), METHOD_POST, ctype);
        conn.setConnectTimeout(connectTimeOut);
        conn.setReadTimeout(readTimeOut);
        conn.getOutputStream().write(buildQuery(params, charset).getBytes(charset));
        response = getResponseAsString(conn);
        return response;
    }
    /**
     * 通过post方法请求数据
     * 
     * @param url 请求的url地址
     * @author wangtao 2014-5-6
     */
    public static String doPost(String url) throws IOException {
    	HttpURLConnection conn = null;
    	String response = "";
    	String ctype = "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET;
    	conn = getConnection(new URL(url), METHOD_POST, ctype);
    	conn.setConnectTimeout(CONNECTTIMEOUT);
    	conn.setReadTimeout(READTIMEOUT);
    	response = getResponseAsString(conn);
    	conn.disconnect();
    	return response;
    }

    /**
     * 
     * @param params 请求参数
     * @return 构建query
     */
    public static String buildQuery(Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtil.areNotEmpty(key, value)) {
                try {
                    sb.append(key).append("=").append(URLEncoder.encode(value, charset));
                } catch (UnsupportedEncodingException e) {}
            }
        }
        return sb.toString();

    }

    public static Map<String, String> splitQuery(String query, String charset) {
        Map<String, String> ret = new HashMap<String, String>();
        if (!StringUtil.isEmpty(query)) {
            String[] splits = query.split("\\&");
            for (String split : splits) {
                String[] keyAndValue = split.split("\\=");
                if (StringUtil.areNotEmpty(keyAndValue) && keyAndValue.length == 2) {
                    try {
                        ret.put(keyAndValue[0], URLDecoder.decode(keyAndValue[1], charset));
                    } catch (UnsupportedEncodingException e) {}
                }
            }
        }
        return ret;
    }
    public static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtil.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + " : " + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }

    }

    private static String getStreamAsString(InputStream input, String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(input, charset));
            String str;
            while ((str = bf.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } finally {
            if (bf != null) {
                bf.close();
                bf = null;
            }
        }

    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtil.isEmpty(ctype)) {
            String[] params = ctype.split("\\;");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("\\=");
                    if (pair.length == 2) {
                        charset = pair[1].trim();
                    }
                }
            }
        }
        return charset;
    }

}
