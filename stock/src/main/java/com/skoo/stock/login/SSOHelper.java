package com.skoo.stock.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SSOHelper {

    private final static String secKey = "111111112222222233333333";

    /**
     * 根据域名相关信息生成加密Cookie名
     *
     * @param request 请求信息
     * @return CookieName
     */
    public final static String getCookie(HttpServletRequest request) {
        String path = request.getContextPath();
        String port = ":" + request.getServerPort();
        if (request.getServerPort() == 80) {
            port = "";
        }
        String basePath = request.getScheme() + "://"
                + request.getServerName() + port + path + "/";
        //System.out.println(basePath);

        String cookieName = DESUtils.encrypt(basePath, secKey);
        return cookieName;
    }

    /**
     * 解析Cookie名
     *
     * @param cookieName 加密Cookie名
     * @return 解密CookieName
     */
    public final static String desCookie(String cookieName) {
        return DESUtils.decrypt(cookieName, secKey);
    }

    /**
     * 根据域名相关信息生成加密Cookie名
     *
     * @param request 请求信息
     * @return CookieName
     */
    public final static String getDomain(HttpServletRequest request) {
        String path = request.getContextPath();
        String port = ":" + request.getServerPort();
        if (request.getServerPort() == 80) {
            port = "";
        }
        String basePath = request.getScheme() + "://"
                + request.getServerName() + port + path + "/";

        return basePath;
    }

    /**
     * 获取票据
     *
     * @param request request
     * @return 票据
     */
    public final static Cookie getTicket(HttpServletRequest request) {
        Cookie ticket = null;
        String cookieName = SSOHelper.getCookie(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    ticket = cookie;
                    break;
                }
            }
        }

        return ticket;
    }


}
