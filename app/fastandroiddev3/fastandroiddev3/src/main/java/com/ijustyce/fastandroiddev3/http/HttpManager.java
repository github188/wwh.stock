package com.ijustyce.fastandroiddev3.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev3.baseLib.utils.RegularUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangchun on 2016/11/10.
 */

public class HttpManager {

    private static Retrofit retrofit;
    private static String baseUrl;
    private static CommonInterceptor intercept;
    private static final int DEFAULT_TIMEOUT = 15;
    private static String httpsCer;
    private static boolean trustAllSll = false;
    private static NetworkInfo networkInfo;
    private static boolean showLog = false;

    static {
        intercept = new CommonInterceptor();
        ConnectivityManager conManager = (ConnectivityManager) IApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = conManager  == null ? null : conManager.getActiveNetworkInfo();
    }

    public static void setShowLog(boolean showLog) {
        HttpManager.showLog = showLog;
        if (retrofit != null) {
            throw new IllegalArgumentException("please call this function before you use HttpManager");
        }
    }

    public static boolean isConnected(){
        return networkInfo == null || networkInfo.isAvailable();
    }

    public static void addCommonParameter(String key, String value) {
        intercept.addCommonParameter(key, value);
    }

    public static void addCommonHeader(String key, String value) {
        intercept.addCommonHeader(key, value);
    }

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    public static void setHttpsCer(String name) {
        httpsCer = name;
    }

    /**
     * 强烈建议您不要信任所有的ssl，而是通过添加你需要信任的证书
     */
    @Deprecated
    public static void trustAllSll(){
        trustAllSll = true;
    }

    protected static OkHttpClient.Builder getBuilder (){
        SSLSocketFactory sslSocketFactory = !trustAllSll && httpsCer != null ? getCertificates() : null;
        OkHttpClient.Builder builder = trustAllSll ? getUnSaveBuilder() : new OkHttpClient.Builder();
                builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory));
        }
        LogInterceptor logging = new LogInterceptor();
        logging.setLevel(LogInterceptor.Level.HEADERS);
        if (showLog) {
            builder.addInterceptor(logging);
        }
        builder.addInterceptor(intercept);
        return builder;
    }

    private static OkHttpClient.Builder getUnSaveBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SSLSocketFactory getCertificates() {
        try {

            InputStream inputStream = IApplication.getInstance().getAssets().open(httpsCer);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(inputStream));

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init(){

        OkHttpClient client = getBuilder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(IJson.getGson()))
                .client(client)
                .build();
    }

    public static <T> T service(Class<T> service){
        if (retrofit == null) {
            if (RegularUtils.isCommonUrl(baseUrl)) {
                init();
            }
        }if (retrofit == null) {
            throw new RuntimeException("please call HttpManager.setBaseUrl(url) before use");
        }
        return retrofit.create(service);
    }

    private static Callback defaultCallBack;
    public static <T> void setDefaultCallBack(Callback<T> callBack){
        defaultCallBack = callBack;
    }

    public static <T> boolean send(final Callback<T> callback, Call<T> call) {
        if (call == null || !isConnected()) return false;
        call.clone().enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (defaultCallBack != null) defaultCallBack.onResponse(call, response);
                if (callback != null) callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t != null) {
                    t.printStackTrace();
                }
                if (defaultCallBack != null) defaultCallBack.onFailure(call, t);
                if (callback != null) callback.onFailure(call, t);
            }
        });
        return true;
    }
}