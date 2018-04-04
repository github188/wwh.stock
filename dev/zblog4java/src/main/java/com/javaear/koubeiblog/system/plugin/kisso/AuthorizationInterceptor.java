package com.javaear.koubeiblog.system.plugin.kisso;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.web.interceptor.SSOPermissionInterceptor;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import com.javaear.koubeiblog.system.plugin.cache.EhCacheContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aooer
 */
public class AuthorizationInterceptor extends SSOPermissionInterceptor {

    @Autowired
    private EhCacheContext cacheContext;

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        Token token = SSOHelper.getToken(request);
        if (token instanceof SSOToken && !StringUtils.isEmpty(((SSOToken) token).getData())) {
            //获取，并传递当前登陆用户的model
            if(modelAndView!=null) {
                modelAndView.getModelMap().put("user", JSON.parseObject(((SSOToken) token).getData(), UserModel.class));
                modelAndView.getModelMap().put("cache", cacheContext.getCacheDTO());
            }
        }
    }

}
