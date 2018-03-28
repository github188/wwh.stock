package com.ijustyce.weekly1601.http;

import com.ijustyce.weekly1601.model.AlbumList;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by yangchun on 2016/11/10.
 */

public interface UserService {

    @POST("album/list.do")
    Call<AlbumList> listAlbum();
}
