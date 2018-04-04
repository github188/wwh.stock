package com.javaear.koubeiblog.desktop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.javaear.generalmapper.GeneralMapper;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.plugin.cache.EhCacheContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aooer
 */
@Controller
public class IndexView {

    @Autowired
    private EhCacheContext cacheContext;

    @Autowired
    private GeneralMapper generalMapper;

    @RequestMapping("*")
    public String defaultUrl(ModelMap modelMap) {
        return "redirect:index.html";
    }

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        modelMap.put("cache", cacheContext.getCacheDTO());
        modelMap.put("title", "koubeiblog");
        modelMap.put("news", generalMapper.selectPage(new Page<>(1, cacheContext.getCacheDTO().getSettingDTO().getDeskPageSize()), new EntityWrapper<>(new ArticleModel())));
        return "desktop/default/index";
    }
}
