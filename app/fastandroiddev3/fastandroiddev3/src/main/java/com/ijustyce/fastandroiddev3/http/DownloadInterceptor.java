package com.ijustyce.fastandroiddev3.http;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by yangchun on 2016/11/10.
 */

public class DownloadInterceptor implements Interceptor {

    private ProgressListener listener;

    public DownloadInterceptor(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) {
        try {
            Response originalResponse = chain.proceed(chain.request());

            return originalResponse.newBuilder()
                    .body(new DownloadResponseBody(originalResponse.body(), listener))
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
