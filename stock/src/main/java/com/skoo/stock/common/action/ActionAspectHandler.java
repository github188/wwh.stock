package com.skoo.stock.common.action;

import com.alibaba.fastjson.JSON;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.domain.IP;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.util.CacheUtil;
import org.apache.log4j.NDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * AOP切面处理（action）
 * 参数打印
 */
@Aspect
@Component
@SuppressWarnings("unchecked")
public class ActionAspectHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ActionAspectHandler.class);

    @Autowired
    private RedisCache redisCache;

    @Around("execution(* com.skoo.stock.api.action.*.*(..))")
    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // log4j 在堆栈顶端加入一个元素（IP地址）
        NDC.clear();
        NDC.push(IP.getRemortIP(request) + " " + request.getRequestURI());
        String currentApi = request.getServletPath().replace("/api/", "");
        Map<String, String> apiMap = (Map<String, String>) CacheUtil.getData(Constant.API_URL);
        String shortApiName = apiMap.get(currentApi);
        LOG.info("[Man_Api " + shortApiName + "]请求参数：" + JSON.toJSON(request.getParameterMap()));

    }
}
