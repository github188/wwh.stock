package cn.hzstk.securities.common.checkcode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        String validateC;
        HttpSession session = request.getSession();
        validateC = (String) session.getAttribute("validateCode");

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
