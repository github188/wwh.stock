package com.skoo.stock.distredis.action;

import com.skoo.stock.distredis.annotation.RCacheEvict;
import com.skoo.stock.distredis.annotation.RCacheable;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.util.ManUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * AOP切面处理（action）
 * 参数打印
 */
@Aspect
@Component
@SuppressWarnings("unchecked")
public class RedisAspectHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RedisAspectHandler.class);

    @Autowired
    private RedisCache redisCache;

    /**
     * 定义缓存逻辑
     */
    @Around("@annotation(com.skoo.stock.distredis.annotation.RCacheable)")
    public Object cache(ProceedingJoinPoint pjp) {
        Object result = null;
        //判断是否开启缓存
        if (!ManUtil.isRedis()) {
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                LOG.error("应用还没有开启缓存！");
            }
            return result;
        }

        Method method = getMethod(pjp);
        RCacheable rCacheable = method.getAnnotation(RCacheable.class);

        String key = parseKey(rCacheable.key(), method, pjp.getArgs());
        String fieldKey = parseKey(rCacheable.field(), method, pjp.getArgs());

        //获取方法的返回类型,让缓存可以返回正确的类型
        Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();

        //使用redis 的hash进行存取，易于管理
        result = redisCache.get(key + fieldKey, returnType);

        if (result == null) {
            try {
                result = pjp.proceed();
                Assert.notNull(fieldKey);
                redisCache.set(key + fieldKey, result, rCacheable.expire());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 定义清除缓存逻辑
     */
    @Around(value = "@annotation(com.skoo.stock.distredis.annotation.RCacheEvict)")
    public Object evict(ProceedingJoinPoint pjp) {
        Method method = getMethod(pjp);
        RCacheEvict cacheEvict = method.getAnnotation(RCacheEvict.class); //获取注解对象实例
        String fieldKey = parseKey(cacheEvict.field(), method, pjp.getArgs()); //注解Key
//        if(cacheEvict.allEntries()){
//            redisCacheCache.delAllEntries(cacheEvict.key());
//        }else{
//            redisCacheCache.del(cacheEvict.key(), fieldKey); //从缓存中删除
//        }
        return null;
    }

    /**
     * 获取被拦截方法对象
     * <p/>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return method;

    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @param key    键
     * @param method 方法
     * @param args   参数
     * @return 缓存Key
     */
    private String parseKey(String key, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
