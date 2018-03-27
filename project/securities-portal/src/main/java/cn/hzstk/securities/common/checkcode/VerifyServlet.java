package cn.hzstk.securities.common.checkcode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

public class VerifyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


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

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("validateCode", cc.getCheckCodeStr());

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
