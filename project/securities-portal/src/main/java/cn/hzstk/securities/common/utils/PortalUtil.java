package cn.hzstk.securities.common.utils;

import cn.hzstk.securities.sys.domain.User;
import net.ryian.core.SystemConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allenwc on 16/4/7.
 */
public class PortalUtil {

    /**
     * 系统名称
     */
    public static final String APPLICATION_NAME_KEY = "application_name";
    /**
     * 用户KEY
     */
    public static final String USER_KEY = "user";
    /**
     * 系统访问地址KEY
     */
    public static final String PORTAL_URL_KEY = "portal_url";
    public static final String BBS_URL_KEY = "bbs_url";
    public static final String MANAGER_URL_KEY = "manager_url";
    public static final String TASK_URL_KEY = "task_url";

    /**
     * 未读消息条数KEY
     */
    public static final String MSG_CNT_KEY = "msg_cnt";

    /**
     * 获得用户
     *
     * @param request
     * @return
     */
    public static User getUser(HttpServletRequest request) {
        return (User) request.getAttribute(USER_KEY);
    }

    /**
     * 设置用户
     *
     * @param request
     */
    public static void setApplicationName(HttpServletRequest request) {
        request.setAttribute(APPLICATION_NAME_KEY,SystemConfig.INSTANCE.getValue(APPLICATION_NAME_KEY));
    }

    /**
     * 设置用户
     *
     * @param request
     */
    public static void setUser(HttpServletRequest request,User user) {
        request.setAttribute(USER_KEY, user);
    }

    public static void setUrls(HttpServletRequest request) {
        request.setAttribute(PORTAL_URL_KEY,SystemConfig.INSTANCE.getValue(PORTAL_URL_KEY));
        request.setAttribute(BBS_URL_KEY,SystemConfig.INSTANCE.getValue(BBS_URL_KEY));
        request.setAttribute(MANAGER_URL_KEY,SystemConfig.INSTANCE.getValue(MANAGER_URL_KEY));
        request.setAttribute(TASK_URL_KEY,SystemConfig.INSTANCE.getValue(TASK_URL_KEY));
    }

    /**
     * 设置未读消息条数
     * @param request
     */
    public static void setMsgCnt(HttpServletRequest request,Integer cnt) {
        request.setAttribute(MSG_CNT_KEY,cnt);
    }

}
