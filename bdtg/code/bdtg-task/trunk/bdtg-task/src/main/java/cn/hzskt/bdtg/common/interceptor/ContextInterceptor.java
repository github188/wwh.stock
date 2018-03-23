package cn.hzskt.bdtg.common.interceptor;

import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.tsk.domain.Member;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 16/4/7.
 */
public class ContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SSOToken token = SSOHelper.getToken(request);
        if(token != null) {
            String memberJSON = token.getData();
            WebUtil.setUser(request, JSON.parseObject(memberJSON, Member.class));
        }
        WebUtil.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
