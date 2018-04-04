package com.javaear.koubeiblog.desktop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.javaear.generalmapper.GeneralMapper;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.plugin.cache.EhCacheContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aooer
 */
@Controller
public class CategoryView {

    @Autowired
    private EhCacheContext cacheContext;

    @Autowired
    private GeneralMapper generalMapper;

    @RequestMapping("category-{id}")
    public String category(@PathVariable String id, HttpServletRequest request, ModelMap modelMap) {
        int pageNo = 1;
        String pageNoStr = request.getParameter("pageNo");
        if (StringUtils.isNotBlank(pageNoStr) && StringUtils.isNumeric(pageNoStr))
            pageNo = Integer.parseInt(pageNoStr);
        if (pageNo < 1)
            pageNo = 1;
        ArticleModel serachModel = new ArticleModel();
        if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id))
            serachModel.setCateId(Integer.parseInt(id));
        Page page = new Page<>(pageNo, cacheContext.getCacheDTO().getSettingDTO().getDeskPageSize());
        modelMap.put("models", generalMapper.selectPage(page, new EntityWrapper<>(serachModel)));
        modelMap.put("page", page);
        modelMap.put("cateId", id);
        modelMap.put("title", "koubeiblog");
        modelMap.put("news", generalMapper.selectPage(new Page<>(1,cacheContext.getCacheDTO().getSettingDTO().getDeskPageSize()), new EntityWrapper<>(serachModel)));
        modelMap.put("cache", cacheContext.getCacheDTO());
        return "desktop/default/category";
    }
}
