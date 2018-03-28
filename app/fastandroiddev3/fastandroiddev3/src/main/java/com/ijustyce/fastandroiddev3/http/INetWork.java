package com.ijustyce.fastandroiddev3.http;

import retrofit2.Callback;

/**
 * Created by yangchun on 2017/2/27.
 */

public class INetWork {

    public static  <T> boolean post(HttpParameter parameter, Callback callback ) {
        return HttpManager.send(callback, HttpManager.service(HttpService.class)
                .post(parameter.getHeader(), parameter.getParameter()));
    }
}