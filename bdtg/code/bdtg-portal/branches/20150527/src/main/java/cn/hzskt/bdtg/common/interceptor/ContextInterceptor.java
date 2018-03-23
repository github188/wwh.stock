package cn.hzskt.bdtg.common.interceptor;

import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.member.service.MsgService;
import cn.hzskt.bdtg.sys.domain.User;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 16/4/7.
 */
public class ContextInterceptor implements HandlerInterceptor {

    @Autowired
    private MsgService msgService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PortalUtil.setUrls(request);
        SSOToken token = SSOHelper.getToken(request);
        if(token != null) {
            String userJSON = token.getData();
            User user = JSON.parseObject(userJSON, User.class);
            PortalUtil.setUser(request, user);
            PortalUtil.setMsgCnt(request,msgService.getUnreadCnt(user.getId()));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
