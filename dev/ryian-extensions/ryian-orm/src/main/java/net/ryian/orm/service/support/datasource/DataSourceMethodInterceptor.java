package net.ryian.orm.service.support.datasource;


import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.support.datasource.annotation.DataSource;

/**
 * Created by allenwc on 14/11/5.
 */
public class DataSourceMethodInterceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        DataSourceContextHolder.clearCustomerType();
        String dsStr = null;
        DataSource ds = null;
        Method method = methodInvocation.getMethod();
        ds = method.getAnnotation(DataSource.class);
        if(ds == null) {
            ds = methodInvocation.getThis().getClass().getAnnotation(DataSource.class);
        }
        if(ds != null) {
            Class<?> clazz = methodInvocation.getThis().getClass();
            ds = clazz.getAnnotation(DataSource.class);
        }
        if(ds != null)
            dsStr = ds.value();
        if(!StringUtils.isEmpty(dsStr)) {
            DataSourceContextHolder.setCustomerType(dsStr);
        }
        Object result = methodInvocation.proceed();
        return result;
    }
}
