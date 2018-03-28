package com.ijustyce.weekly1601.model;

import com.ijustyce.fastandroiddev3.http.HttpResultModel;
import com.ijustyce.fastandroiddev3.irecyclerview.IResponseData;
import com.ijustyce.weekly1601.bean.AlbumBean;

import java.util.ArrayList;

/**
 * Created by yangchun on 2016/11/12.
 */

public class AlbumList extends IResponseData<AlbumBean> implements HttpResultModel{

    private ArrayList<AlbumBean> albums;
    @Override
    public ArrayList<AlbumBean> getList() {
        return albums;
    }

    @Override
    public boolean needLogin() {
        return false;
    }
}
