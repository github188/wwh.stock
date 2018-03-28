package com.ijustyce.weekly1601.model;

import com.ijustyce.fastandroiddev3.http.HttpResultModel;

/**
 * Created by yangchun on 2016/11/19.
 */

public class Login implements HttpResultModel{

    public String data;
    public String code;
    public String message;

    @Override
    public boolean needLogin() {
        return false;
    }
}
