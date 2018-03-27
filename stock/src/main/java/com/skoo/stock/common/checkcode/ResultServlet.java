package com.skoo.stock.common.checkcode;

import com.skoo.common.SystemConfig;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.SSOHelper;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        Cookie cookieTicket = SSOHelper.getTicket(request);
        String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
        String validateC;
        // redis need 验证码取得
        String redisActive = SystemConfig.INSTANCE.getValue("redisactive");
        if ("true".equals(redisActive)) {
            validateC = redisCache.getString(RedisKey.VALIDATE_CODE + jsessionId);
        } else {
            HttpSession session = request.getSession();
            validateC = (String) session.getAttribute("validateCode");
        }

        String veryCode = request.getParameter("veryCode");

        PrintWriter out = response.getWriter();
        if (veryCode == null || "".equals(veryCode)) {
            out.print("false");
        } else {
            // equalsIgnoreCase如果两个字符串的长度相等，并且两个字符串中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的
            // equals则严格区别大小写
            if (validateC.equalsIgnoreCase(veryCode)) {
                out.print("true");
            } else {
                out.print("false");
            }
        }
        out.flush();
        out.close();
    }
}
