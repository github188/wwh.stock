package com.ijustyce.fastandroiddev3.http;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yangchun on 2016/11/10.
 */
interface HttpService {

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);

    @POST
    @Multipart
    Call<ResponseBody> upload(@Url String uploadUrl, @Part MultipartBody.Part[] file);

    @POST
    @Multipart
    Call<ResponseBody> upload(@Url String uploadUrl, @Part MultipartBody.Part file);

    @POST
    @FormUrlEncoded
    Call<? extends HttpResultModel> post(@FieldMap HashMap<String, String> parameter,
                                           @HeaderMap HashMap<String, String> headerMap);

    @GET
    Call get(@QueryMap HashMap<String, String> parameter,
                    @HeaderMap HashMap<String, String> headerMap);
}
