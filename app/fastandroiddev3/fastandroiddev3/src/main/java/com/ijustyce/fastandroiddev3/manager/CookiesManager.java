package com.ijustyce.fastandroiddev3.manager;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.ijustyce.fastandroiddev3.IApplication;

/**
 * Created by yangchun on 16/5/26.  cookies manager
 */
public class CookiesManager {

    private static CookieManager cookieManager;
    static {
        CookieSyncManager.createInstance(IApplication.getInstance());
        cookieManager = CookieManager.getInstance();
        try {       //  utest 显示有一款手机setAcceptCookie 时找不到方法
            cookieManager.setAcceptCookie(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    public static void set(String url, String cookie){
        cookieManager.setCookie(url, cookie);
    }

    public static String get(String url){
        return cookieManager.getCookie(url);
    }
}
