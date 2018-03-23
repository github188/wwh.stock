package com.zjhcsoft.struc.fetch.wrapper;

import com.alibaba.fastjson.JSON;
import com.zjhcsoft.struc.fetch.CommonFetcher;
import com.zjhcsoft.struc.util.datasource.DataSourceHandler;
import com.zjhcsoft.struc.util.httpclient.HttpProtocolHandler;
import com.zjhcsoft.struc.util.httpclient.HttpRequest;
import com.zjhcsoft.struc.util.httpclient.HttpResponse;
import com.zjhcsoft.struc.util.httpclient.HttpResultType;

import java.io.IOException;

/**
 * Created by allenwc on 14/11/19.
 */
public class HttpCommonFetcherWrapper extends CommonFetcherWrapper{

    public HttpCommonFetcherWrapper(CommonFetcher fetcher) {
        super(fetcher);
    }

    public static String httpExecute(String url, String charset, String method,String proxy,int port) {
        HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setUrl(url);
        request.setCharset(charset);
        request.setMethod(method);
        String content = null;
        try {
            System.err.println(JSON.toJSONString(request));
            HttpResponse response = handler.execute(request, "", "",proxy,port);
            if (response !=null) {
            	 content = response.getStringResult(charset);
            }
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
        return content;
    }
    
    public static String httpExecute(String url, String charset, String method) {
        HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setUrl(url);
        request.setCharset(charset);
        request.setMethod(method);
        String content = null;
        try {
            System.err.println(JSON.toJSONString(request));
            HttpResponse response = handler.execute(request, "", "");
            if (response !=null) {
            	 content = response.getStringResult(charset);
            }
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
        return content;
    }
    public static String httpExecute(String url, String charset, String method,int timeout, int connectionTimeout) {
        HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setUrl(url);
        request.setCharset(charset);
        request.setMethod(method);
        request.setTimeout(timeout);
        request.setConnectionTimeout(connectionTimeout);
        String content = null;
        try {
            System.err.println(JSON.toJSONString(request));
            HttpResponse response = handler.execute(request, "", "");
            content = response.getStringResult(charset);
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
        return content;
    }
  
    @Override
    public Object fetch() {
        return httpExecute(fetcher.getUrl(), DataSourceHandler.urlPattern.getCharset(),DataSourceHandler.urlPattern.getHttpmethod());
    }

}
