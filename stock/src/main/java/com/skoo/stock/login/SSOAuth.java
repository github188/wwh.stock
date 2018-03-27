package com.skoo.stock.login;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.domain.Ticket;
import com.skoo.stock.login.domain.UserInfo;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.service.OperateLogService;
import com.skoo.stock.util.EncryptUtil;
import com.skoo.stock.util.ManUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Servlet implementation class SSOAuth
 */
public class SSOAuth extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final static SSOService ssoService = SpringContextUtil.getBean(SSOService.class);
    private static final Log logger = LogFactory.getLog(SSOAuth.class);

    private final static OperateLogService operateLogService = SpringContextUtil.getBean(OperateLogService.class);
    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    /**
     * 单点登录标记
     */
    private static Map<String, Ticket> tickets;
    /**
     * 是否安全协议
     */
    private boolean secure;

    private UserInfo ufo;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * ticket有效时间
     */
    private int ticketTimeout;

    /**
     * 回收ticket线程池
     */
    private ScheduledExecutorService schedulePool;

    /**
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        tickets = new ConcurrentHashMap<>();
        secure = Boolean.parseBoolean(config.getInitParameter("secure"));
        secretKey = config.getInitParameter("secretKey");
        ticketTimeout = Integer.parseInt(config.getInitParameter("ticketTimeout"));

        // redis need 初始化
        String redisActive = SystemConfig.INSTANCE.getValue("redisactive");
        if (!"true".equals(redisActive)) {
            schedulePool = Executors.newScheduledThreadPool(1);
            schedulePool.scheduleAtFixedRate(new RecoverTicket(tickets), 5, 10, TimeUnit.SECONDS);
        }
        //schedulePool.scheduleAtFixedRate(new RecoverTicket(tickets), ticketTimeout * 60 / 60, 1, TimeUnit.MINUTES);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("act");
        logger.debug("SSO Server [" + action + "] 操作！");
        if ("preLogin".equals(action)) {
            preLogin(request, response);
        } else if ("login".equals(action)) {
            doLogin(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else if ("authTicket".equals(action)) {
            authTicket(request, response);
        } else {
            logger.error("SSO Server [" + action + "] 操作！");
            String gotoURL = request.getParameter("goURL");
            if (gotoURL == null) {
                gotoURL = request.getRequestURL().toString();
            }

            gotoURL = gotoURL.replace("/logout", "");
            gotoURL = gotoURL.replace("/SSOAuth", "");

            String cookieName = SSOHelper.getCookie(request);
            String userType = request.getParameter("uType");

            if (StringUtils.isEmpty(userType)) {
                userType = "1";
            }

            String URL = "SSOAuth?act=preLogin"
                    + "&goURL=" + gotoURL
                    + "&uType=" + userType
                    + "&cn=" + cookieName;


            request.getRequestDispatcher(URL).forward(request, response);
        }
        out.close();
    }

    @Override
    public void destroy() {
        if (schedulePool != null) schedulePool.shutdown();
    }

    private void preLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie ticket = null;
        Cookie[] cookies = request.getCookies();
        String cookieName = request.getParameter("cookieName");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    ticket = cookie;
                    break;
                }
            }
        }

        HttpSession session = request.getSession();//获取SESSION
        String needverify = (String) session.getAttribute("needVerify");
        if (needverify == null) {
            needverify = "none";
        }
        if (ticket == null) {
            request.getRequestDispatcher("login.jsp?display=" + needverify).forward(request, response);
        } else {
            String encodedTicket = ticket.getValue();
            String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
            if (tickets.containsKey(decodedTicket)) {
                String setCookieURL = request.getParameter("setCookieURL");
                String goURL = request.getParameter("goURL");
                if (setCookieURL != null)
                    response.sendRedirect(setCookieURL + "?ticket=" + encodedTicket + "&expiry=" + ticket.getMaxAge() + "&goURL=" + goURL);
            } else {
                request.getRequestDispatcher("login.jsp?display=" + needverify).forward(request, response);
            }
        }
    }

    private void authTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder result = new StringBuilder("{");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String encodedTicket = request.getParameter("encodedTicket");
        if (encodedTicket == null) {
            result.append("\"error\":true,\"errorInfo\":\"Ticket can not be empty!\"");
        } else {
            String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
            // redis need 校验
            //String jsessionId = encodedTicket;
            String keyUserInfo = RedisKey.USERINFO + encodedTicket;
            boolean ticketon;
            Ticket ticket = null;
            if (ManUtil.isRedis()) {
                ticketon = redisCache.exists(RedisKey.COOKIE + decodedTicket);
                if (ticketon) {
                    HttpSession session = request.getSession(true);
                    int sessionMaxInactiveInterval = session.getMaxInactiveInterval();//session过期时间
                    redisCache.expire(keyUserInfo, sessionMaxInactiveInterval);//session时间
                    ticket = redisCache.get(RedisKey.COOKIE + decodedTicket, Ticket.class);
                }
            } else {
                ticketon = tickets.containsKey(decodedTicket);
                ticket = tickets.get(decodedTicket);
            }

            if (ticketon) {
                result.append("\"error\":false,\"username\":\"");
                result.append(ticket.getUsername());
                result.append("\"");
            } else {
                result.append("\"error\":true,\"errorInfo\":\"Ticket is not found!\"");
            }
        }
        result.append("}");
        out.print(result);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder result = new StringBuilder("{");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String encodedTicket = request.getParameter("encodedTicket");
        if (encodedTicket == null) {
            result.append("\"error\":true,\"errorInfo\":\"Ticket can not be empty!\"");
        } else {
            String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
            // redis need 删除
            if (ManUtil.isRedis()) {
                String keyUserInfo = RedisKey.USERINFO + encodedTicket;
                redisCache.del(keyUserInfo);
                redisCache.del(RedisKey.COOKIE + decodedTicket);
            } else {
                tickets.remove(decodedTicket);
            }
            result.append("\"error\":false");
        }
        result.append("}");
        out.print(result);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String cookieName = request.getParameter("cn"); // CookieName
        String userType = request.getParameter("uType");

        HttpSession session = request.getSession();//获取SESSION

        if (StringUtils.isEmpty(userType)) {
            userType = "1";
        }

        String errorInfo = "";

        if (StringUtils.isEmpty(username)) {
            errorInfo = "登录名不能为空！";
        } else if (StringUtils.isEmpty(password)) {
            errorInfo = "密码不能为空！";
        }

        // 校验验证码
        Cookie cookieTicket = SSOHelper.getTicket(request);
        String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
        String needverify;
        // redis need 验证码取得
        if (ManUtil.isRedis()) {
            needverify = redisCache.getString(RedisKey.NEED_VERIFY + jsessionId);
        } else {
            needverify = (String) session.getAttribute("needVerify");
        }
        if (needverify == null) {
            needverify = "none";
        }
        if ("block".equals(needverify)) {
            if ("".equals(errorInfo)) {
                String validateC;
                if (ManUtil.isRedis()) {
                    validateC = redisCache.getString(RedisKey.VALIDATE_CODE + jsessionId);
                } else {
                    validateC = (String) session.getAttribute("validateCode");
                }
                String veryCode = request.getParameter("verifycode");

                if (veryCode == null || "".equals(veryCode)) {
                    errorInfo = "验证码不能为空！";
                } else {
                    // equalsIgnoreCase如果两个字符串的长度相等，并且两个字符串中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的
                    // equals则严格区别大小写
                    if (!veryCode.equalsIgnoreCase(validateC)) {
                        errorInfo = "验证码错误！";
                    }
                }
            }
        }

        // 校验密码
        if ("".equals(errorInfo)) {
            password = EncryptUtil.encrypt(password);
            Map<String, String> param = new HashMap<>();
            param.put("userId", username);
            param.put("password", password);
            param.put("domain", SSOHelper.desCookie(cookieName));
            param.put("userType", userType);
            List<UserInfo> userInfo = ssoService.queryData("UserInfo_list", param);
            if (userInfo == null || userInfo.size() != 1) {
                errorInfo = "登录名或密码错误！";

                if (ManUtil.isRedis()) {
                    redisCache.set(RedisKey.NEED_VERIFY + jsessionId, "block", RedisKey.KEY_TIME);
                } else {
                    session.setAttribute("needVerify", "block");
                }

                password = "";
                operateLogService.wirteLog(request, OperateLog.LOGIN_FAILURE_TITLE, "username=" + username);
            } else {

                if (ManUtil.isRedis()) {
                    redisCache.set(RedisKey.NEED_VERIFY + jsessionId, "none", RedisKey.KEY_TIME);
                } else {
                    session.setAttribute("needVerify", "none");
                }

                ufo = userInfo.get(0);
                operateLogService.wirteLog(request, OperateLog.LOGIN_SUCCESS_TITLE, "username=" + username);
            }
        }


        // 验证失败错误提交页面
        if (!"".equals(errorInfo)) {
            needverify = (String) session.getAttribute("needVerify");
            request.getRequestDispatcher("login.jsp?errorInfo="
                    + errorInfo + "&username=" + username
                    + "&uType=" + userType
                    + "&display=" + needverify
                    + "&password=" + password).forward(request, response);
        } else {

            String uinfo = URLEncoder.encode(JSONObject.fromObject(ufo).toString(), "UTF-8");

            String ticketKey = UUID.randomUUID().toString().replace("-", "");

            String encodedticketKey = DESUtils.encrypt(ticketKey, secretKey);

            Timestamp createTime = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(createTime);
            cal.add(Calendar.MINUTE, ticketTimeout);
            Timestamp recoverTime = new Timestamp(cal.getTimeInMillis());
            Ticket ticket = new Ticket(username, createTime, recoverTime);

            // redis need 登录
            if (ManUtil.isRedis()) {
                redisCache.set(RedisKey.COOKIE + ticketKey, ticket, ticketTimeout * 60);
            } else {
                tickets.put(ticketKey, ticket);
            }

            String[] checks = request.getParameterValues("autoAuth");
            int expiry = -1;
            if (checks != null && "1".equals(checks[0])) {
                expiry = 7 * 24 * 3600;
            }
            Cookie cookie = new Cookie(cookieName, encodedticketKey);
            cookie.setSecure(secure);// 为true时用于https
            cookie.setMaxAge(expiry);
            cookie.setPath("/");
            response.addCookie(cookie);

            String setCookieURL = SSOHelper.desCookie(cookieName) + "setCookie";
            String goURL = request.getParameter("goURL");

            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script type='text/javascript'>");
            out.print("document.write(\"<form id='url' method='post' action='" + setCookieURL + "'>\");");
            out.print("document.write(\"<input type='hidden' name='goURL' value='" + goURL + "' />\");");
            out.print("document.write(\"<input type='hidden' name='ticket' value='" + encodedticketKey + "' />\");");
            out.print("document.write(\"<input type='hidden' name='expiry' value='" + expiry + "' />\");");
            out.print("document.write(\"<input type='hidden' name='uinfo' value='" + uinfo + "' />\");");
            out.print("document.write('</form>');");
            out.print("document.getElementById('url').submit();");
            out.print("</script>");
        }
    }

}
