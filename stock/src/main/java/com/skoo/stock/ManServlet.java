package com.skoo.stock;

import com.skoo.common.SystemConfig;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.SSOHelper;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.login.domain.UserInfo;
import com.skoo.stock.util.ManUtil;
import com.skoo.stock.util.ValidateUtil;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManServlet extends DispatcherServlet {

    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String base = "";
        if (!"/".equals(request.getContextPath())) {
            base = request.getContextPath();
        }
        mv.addObject("serverinfo", SystemConfig.INSTANCE.getValue("serverinfo"));
        mv.addObject("base", base);
        mv.addObject("assbase", SystemConfig.INSTANCE.getValue("assDomain"));

        mv.addObject("userType", UserSession.getUserType());
        mv.addObject("sessionid", request.getSession().getId());

        String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());

        Cookie cookieTicket = SSOHelper.getTicket(request);
        String sessionKey = SSOHelper.getCookie(request);
        String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
        String havesession = null;
        // redis need 初始化用户信息
        if (ManUtil.isRedis()) {
            String keyUserInfo = RedisKey.USERINFO + jsessionId;
            if (redisCache.exists(keyUserInfo)) {
                UserInfo userInfo = redisCache.get(keyUserInfo, UserInfo.class);
                if (userInfo != null) {
                    havesession = "true";//有登录信息
                }
            }
        } else {
            HttpSession session = request.getSession();
            if (session != null) {
                UserInfo userInfo = (UserInfo) session.getAttribute(sessionKey);
                if (userInfo != null) {
                    havesession = "true";//有登录信息
                }
            }
        }

        if (havesession == null && !ValidateUtil.isNotCheckURL(uri, Constant.NOT_CHECK_URL)) {
            response.sendRedirect(base + Constant.LOGOUT_URL);
        } else {
            super.render(mv, request, response);
        }
    }


}
