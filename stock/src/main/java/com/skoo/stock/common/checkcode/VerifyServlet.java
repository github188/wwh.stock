package com.skoo.stock.common.checkcode;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.SSOHelper;
import com.skoo.stock.util.ManUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;

public class VerifyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    /**
     * 初始化验证图片属性
     */
    public void init() throws ServletException {
        if (getInitParameter("fontsize") != null) {
            int fontsize = Integer.parseInt(getInitParameter("fontsize"));
            CheckCode.setFontSize(fontsize);
        }

        if (getInitParameter("length") != null) {
            int length = Integer.parseInt(getInitParameter("length"));
            CheckCode.setLength(length);
        }

        if (getInitParameter("usenoise") != null) {
            if ("false".equals(getInitParameter("usenoise"))) {
                CheckCode.setUseNoise(false);
            }
        }

        if (getInitParameter("usecurve") != null) {
            if ("false".equals(getInitParameter("usecurve"))) {
                CheckCode.setUseCurve(false);
            }
        }
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
        CheckCode cc = CheckCode.fresh();
        //图像buffer
        BufferedImage buffImg = cc.getBuffImg();

        Cookie cookieTicket = SSOHelper.getTicket(req);
        String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
        // redis need 验证码取得
        if (ManUtil.isRedis()) {
            redisCache.set(RedisKey.VALIDATE_CODE + jsessionId, cc.getCheckCodeStr(), 300);
        } else {
            // 将四位数字的验证码保存到Session中。
            HttpSession session = req.getSession();
            session.setAttribute("validateCode", cc.getCheckCodeStr());
        }

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/png");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }
}
