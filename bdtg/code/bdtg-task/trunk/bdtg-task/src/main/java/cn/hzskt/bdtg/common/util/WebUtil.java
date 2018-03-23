package cn.hzskt.bdtg.common.util;

import cn.hzskt.bdtg.tsk.domain.Member;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allenwc on 16/4/7.
 */
public class WebUtil {

    /**
     * 用户KEY
     */
    public static final String USER_KEY = "user";
    /**
     * 任务KEY
     */
    public static final String TASK_ID_KEY = "tid";

    /**
     * 获得用户
     *
     * @param request
     * @return
     */
    public static Member getUser(HttpServletRequest request) {
        return (Member) request.getAttribute(USER_KEY);
    }


    /**
     * 设置用户
     *
     * @param request
     */
    public static void setUser(HttpServletRequest request,Member user) {
        request.setAttribute(USER_KEY, user);
    }

    public static Long getTid(HttpServletRequest request) {
        return (Long) request.getAttribute(TASK_ID_KEY);
    }

    public static void setTid(HttpServletRequest request,Long tid) {
        request.setAttribute(TASK_ID_KEY, tid);
    }

}
