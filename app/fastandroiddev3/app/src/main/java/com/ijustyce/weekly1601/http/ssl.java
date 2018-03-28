package com.ijustyce.weekly1601.http;

import com.ijustyce.weekly1601.model.Login;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yangchun on 2016/11/19.
 */

public interface ssl {

    @POST("user/addUser.do")
    Call<Login> doLogin(@Query("username") String username, @Query("email")String email,
                        @Query("password") String password, @Query("type") int type);
}
