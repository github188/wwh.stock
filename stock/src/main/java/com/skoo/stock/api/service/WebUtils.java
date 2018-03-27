package com.skoo.stock.api.service;

import com.skoo.stock.api.domain.FileItem;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 网络工具类。
 *
 * @author wangfei
 * @since 2014-04-18
 */
public abstract class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private WebUtils() {
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param params     文本请求参数
     * @param fileParams 文件请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
        } else {
            return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
        }
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url            请求地址
     * @param params         文本请求参数
     * @param fileParams     文件请求参数
     * @param charset        字符集，如UTF-8, GBK, GB2312
     * @param connectTimeout 连接超时
     * @param readTimeout    读超时
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param params     文本请求参数
     * @param fileParams 文件请求参数
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @param headerMap  需要传递的header头，可以为空
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, charset, connectTimeout, readTimeout, headerMap);
        } else {
            return _doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
        }
    }

    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        return _doPost(url, ctype, content, connectTimeout, readTimeout, new HashMap<String, String>());
    }

    private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } catch (IOException e) {
            log(e, url, parseParam(new String(content, "UTF-8")));
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String boundary = System.currentTimeMillis() + ""; // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
                conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }

            try {
                out = conn.getOutputStream();

                byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

                // 组装文本请求参数
                Set<Entry<String, String>> textEntrySet = params.entrySet();
                for (Entry<String, String> textEntry : textEntrySet) {
                    byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
                    out.write(entryBoundaryBytes);
                    out.write(textBytes);
                }

                // 组装文件请求参数
                Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
                for (Entry<String, FileItem> fileEntry : fileEntrySet) {
                    FileItem fileItem = fileEntry.getValue();
                    if (fileItem.getContent() == null) {
                        continue;
                    }
                    byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                    out.write(entryBoundaryBytes);
                    out.write(fileBytes);
                    out.write(fileItem.getContent());
                }

                // 添加请求结束标志
                byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
                out.write(endBoundaryBytes);
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset)
            throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition: form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            try {
                conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype, null);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }

            try {
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static byte[] doGetByte(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        byte[] rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            try {
                conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype, null);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }

            try {
                rsp = getResponseAsByte(conn);
            } catch (IOException e) {
                log(e, url, params);
                throw e;
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
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
        conn.setRequestProperty("User-Agent", "longhz-sdk-java");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (isEmpty(query)) {
            return url;
        }

        if (isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (!isEmpty(name) && !isEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    protected static byte[] getResponseAsByte(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        long length = conn.getContentLength();
        if (es == null) {
            return getStreamAsByte(conn.getInputStream(), length, charset);
        } else {
            return getStreamAsByte(es, length, charset);
        }
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage() + "\r\n" + msg);
            }
        }
    }

    private static byte[] getStreamAsByte(InputStream stream, long length, String charset) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] buff = new byte[1024];
            long total = 0;
            int once = 0;
            while (total < length) {
                once = stream.read(buff);
                bos.write(buff, 0, once);
                total += once;
            }

            return bos.toByteArray();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (!isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (!isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * @param e
     * @param url
     * @param params
     */
    public static void log(Exception e, String url, Map<String, String> params) {
        _log(e, null, url, params);
    }

    private static Map<String, String> parseParam(String contentString) {
        Map<String, String> params = new HashMap<String, String>();
        if (contentString == null || contentString.trim().equals("")) {
            return params;
        }
        String[] paramsArray = contentString.split("\\&");
        if (paramsArray != null) {
            for (String param : paramsArray) {
                String[] keyValue = param.split("=");
                if (keyValue != null && keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    /**
     * 通讯错误日志
     */
    private static void _log(Exception e, HttpURLConnection conn, String url, Map<String, String> params) {
        String urlStr = null;
        String rspCode = "";
        if (conn != null) {
            try {
                urlStr = conn.getURL().toString();
                rspCode = "HTTP_ERROR_" + conn.getResponseCode();
            } catch (IOException ioe) {
            }
        } else {
            urlStr = url;
            rspCode = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(urlStr);// 请求URL
        sb.append("^_^");
        sb.append(params);
        sb.append("^_^");
        sb.append(rspCode);
    }

    private static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }


}
