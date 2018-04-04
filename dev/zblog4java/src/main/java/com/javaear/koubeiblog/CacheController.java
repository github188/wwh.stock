package com.javaear.koubeiblog;

import com.javaear.generalmapper.GeneralMapper;
import com.javaear.koubeiblog.system.plugin.cache.EhCacheContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 缓存控制器，前后台所有controller顶层父类
 * @author aooer 2016/10/21.
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class CacheController {

    @Autowired
    protected EhCacheContext cacheContext;

    @Autowired
    protected GeneralMapper generalMapper;
}
