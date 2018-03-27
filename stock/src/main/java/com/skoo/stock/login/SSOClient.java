package com.skoo.stock.login;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.common.service.LoadTask;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.domain.UserInfo;
import com.skoo.stock.util.ManUtil;
import com.skoo.stock.util.ValidateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

/**
 * Servlet Filter implementation class SSOAuth
 */
public class SSOClient implements Filter {

    private static final Log logger = LogFactory.getLog(SSOClient.class);
    protected FilterConfig filterConfig = null;
    private String ssoService;
    private Set<String> ignoreTypeList = new HashSet<>();

    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        this.filterConfig = fConfig;
        ssoService = fConfig.getInitParameter("SSOService");

        // 取得过滤外类型列表
        String ignoreTypeListStr = filterConfig.getInitParameter("ignoreTypes");
        if (ignoreTypeListStr != null) {
            String[] params = ignoreTypeListStr.split(",");
            for (String param : params) {
                ignoreTypeList.add(param.trim());
            }
        }
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 过滤对象外类型
        if (!checkIgnoreType(request)) {
            // 过滤对象外URL
            if (!checkRequestURI(request)) {
                Cookie cookieTicket = SSOHelper.getTicket(request);

                //如果是ajax请求响应头会有，x-requested-with；
                if (request.getHeader("x-requested-with") != null
                        && request.getHeader("x-requested-with")
                        .equalsIgnoreCase("XMLHttpRequest")) {

                    String havesession = null;
                    String sessionKey = SSOHelper.getCookie(request);
                    String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
                    // redis need Session检测用户信息
                    if (ManUtil.isRedis()) {
                        String keyUserInfo = RedisKey.USERINFO + jsessionId;
                        if (redisCache.exists(keyUserInfo)) {
                            UserInfo userInfo = redisCache.get(keyUserInfo, UserInfo.class);
                            if (userInfo != null) {
                                havesession = "true";//有登录信息
                            }
                        }
                    } else {
                        //在响应头设置session状态
                        HttpSession session = request.getSession();
                        if (session != null) {
                            UserInfo userInfo = (UserInfo) session.getAttribute(sessionKey);
                            if (userInfo != null) {
                                havesession = "true";//有登录信息
                            }
                        }
                    }

                    // Session 过期，需要重新登录
                    if (havesession == null) {
                        String base = "";
                        if (!"/".equals(request.getContextPath())) {
                            base = request.getContextPath();
                        }
                        response.setHeader("sessionstatus", "sessionOut");
                        response.getWriter().print("<div style='text-align:center;'>" +
                                "<a class='left' href='' target='_blank'><img src='../images/jimu/cry.gif' width='80' height='80' alt='' /></a>" +
                                "<dl id='yz'><dt>非常抱歉！！！</dt><dt>Session过期，请重新" +
                                "<a style='color: #FF0000' href='" + base + Constant.LOGOUT_URL + "'>登录<a>" + "!</dt><dl></div>");
                        return;
                    }
                }

                String path = request.getContextPath();
                String gotoURL = request.getParameter("goURL");
                if (gotoURL == null) {
                    gotoURL = request.getRequestURL().toString();
                }

                gotoURL = gotoURL.replace("/logout", "");

                String cookieName = SSOHelper.getCookie(request);

                ssoService = SSOHelper.desCookie(cookieName) + "SSOAuth";

                String URL = ssoService + "?act=preLogin"
                        + "&goURL=" + gotoURL
                        + "&cn=" + cookieName;

                String sessionKey = SSOHelper.getCookie(request);
                String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
                UserInfo userInfo = null;
                // redis need 线程用户信息设定
                if (ManUtil.isRedis()) {
                    String keyUserInfo = RedisKey.USERINFO + jsessionId;
                    if (redisCache.exists(keyUserInfo)) {
                        userInfo = redisCache.get(keyUserInfo, UserInfo.class);
                        UserSession.setUserInfo(userInfo);
                    }
                } else {
                    HttpSession session = null;
                    session = request.getSession();
                    if (session != null) {
                        userInfo = (UserInfo) session.getAttribute(sessionKey);
                        UserSession.setUserInfo(userInfo);
                    }
                }

                if (request.getRequestURI().equals(path + "/logout")) {
                    logger.debug("SSO Client [doLogout] 操作！");
                    doLogout(request, response, chain, cookieTicket, URL);
                } else if (request.getRequestURI().equals(path + "/setCookie")) {
                    logger.debug("SSO Client [setCookie] 操作！");
                    setCookie(request, response);
                } else if (cookieTicket != null) {
                    logger.debug("SSO Client [authCookie] 操作！");
                    authCookie(request, response, chain, cookieTicket, URL);
                } else {
                    try {
                        LoadTask loadJob = SpringContextUtil.getBean(LoadTask.class);
                        loadJob.initTask();
                    } catch (Exception e) {
                        logger.error("save", e);
                    }

                    logger.debug("SSO Client [sendRedirect] 操作！");
                    response.sendRedirect(URL);
                }

                return;

            }
        }

        chain.doFilter(req, resp);
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    private void setCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieName = SSOHelper.getCookie(request);
        Cookie ticket = new Cookie(cookieName, request.getParameter("ticket"));
        ticket.setPath("/");
        ticket.setMaxAge(Integer.parseInt(request.getParameter("expiry")));
        response.addCookie(ticket);

        String uinfo = URLDecoder.decode(request.getParameter("uinfo"), "UTF-8");
        UserInfo user = (UserInfo) JSONObject.toBean(JSONObject.fromObject(uinfo), UserInfo.class);

        // redis need 用户信息设定
        String jsessionId = ticket.getValue();
        if (ManUtil.isRedis()) {
            String keyUserInfo = RedisKey.USERINFO + jsessionId;
            if (user != null) {
                HttpSession session = request.getSession(true);
                int sessionMaxInactiveInterval = session.getMaxInactiveInterval();//session过期时间
                redisCache.set(keyUserInfo, user, sessionMaxInactiveInterval);
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(cookieName, user);
        }

        String gotoURL = request.getParameter("goURL");
        if (gotoURL != null) {
            response.sendRedirect(gotoURL);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Cookie ticket, String URL) throws IOException, ServletException {
        NameValuePair[] params = new NameValuePair[2];
        params[0] = new NameValuePair("act", "logout");
        params[1] = new NameValuePair("encodedTicket", ticket.getValue());
        try {
            // 清空session
            HttpSession session = request.getSession();
            session.invalidate();
            post(request, response, chain, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            response.sendRedirect(URL);
        }
    }

    private void authCookie(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Cookie ticket, String URL) throws IOException, ServletException {
        NameValuePair[] params = new NameValuePair[2];
        params[0] = new NameValuePair("act", "authTicket");
        params[1] = new NameValuePair("encodedTicket", ticket.getValue());
        try {
            JSONObject result = post(request, response, chain, params);
            if (result != null && result.getBoolean("error")) {
                response.sendRedirect(URL);
            } else {
                request.setAttribute("username", result.getString("username"));
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect(URL);
            throw new RuntimeException(e);
        }
    }

    private JSONObject post(HttpServletRequest request, HttpServletResponse response, FilterChain chain, NameValuePair[] params) throws IOException, ServletException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(ssoService);
        postMethod.addParameters(params);
        switch (httpClient.executeMethod(postMethod)) {
            case HttpStatus.SC_OK:
                JSONObject object = JSONObject.fromObject(postMethod.getResponseBodyAsString());
                return object;
            default:
                // 其它处理
                return null;
        }
    }

    private boolean checkIgnoreType(HttpServletRequest request) {
        String ignoreType = request.getRequestURI();
        ignoreType = ignoreType.substring(ignoreType.lastIndexOf(".") + 1);
        return ignoreTypeList.contains(ignoreType);
    }

    private boolean checkRequestURI(HttpServletRequest request) {
        String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
        return ValidateUtil.isNotCheckURL(uri, Constant.NOT_CHECK_URL);
    }

}
