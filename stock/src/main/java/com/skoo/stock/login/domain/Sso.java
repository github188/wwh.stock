package com.skoo.stock.login.domain;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Sso {

    private static final long serialVersionUID = 1L;

    /**
     * 动作 *
     */
    private String action;

    /**
     * cookieURL *
     */
    private String cookieURL;

    /**
     * gotoURL *
     */
    private String gotoURL;

    /**
     * 企业ID *
     */
    private String cookieName;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCookieURL() {
        return cookieURL;
    }

    public void setCookieURL(String cookieURL) {
        this.cookieURL = cookieURL;
    }

    public String getGotoURL() {
        return gotoURL;
    }

    public void setGotoURL(String gotoURL) {
        this.gotoURL = gotoURL;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
