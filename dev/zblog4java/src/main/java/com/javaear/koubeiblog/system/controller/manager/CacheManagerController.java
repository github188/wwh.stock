package com.javaear.koubeiblog.system.controller.manager;

import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.model.ConfigModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aooer
 */
@Component
public class CacheManagerController extends AbstractManagerController<ConfigModel> {

    /**
     * 清空缓存
     *
     * @return 重定向
     */
    @RequestMapping("cache-reload")
    public String list() {
        cacheContext.clearCacheDTO();
        return "redirect:/system/index.html";
    }
}
