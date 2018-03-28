package com.ijustyce.weekly1601;

import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.http.HttpManager;

/**
 * Created by yangchun on 2016/11/10.
 */

public class AppApplication extends IApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.setBaseUrl("https://ssl.ijustyce.win/webapp/");
        HttpManager.trustAllSll();
     //   HttpManager.setHttpsCer("ssl.crt");
    }
}
