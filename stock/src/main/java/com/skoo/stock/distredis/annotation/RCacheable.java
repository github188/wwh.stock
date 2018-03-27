package com.skoo.stock.distredis.annotation;

import com.skoo.stock.common.RedisKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 *
 * @author gomiten@163.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RCacheable {
    String key();

    String field();

    String type() default RedisKey.TYPE_STRING;

    int expire() default 3600;
}