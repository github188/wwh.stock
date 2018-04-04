package com.javaear.koubeiblog.desktop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.javaear.koubeiblog.CacheController;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aooer
 */
@Controller
public class ArticleView extends CacheController {

    @RequestMapping("article/{id}")
    public String category(@PathVariable String id, HttpServletRequest request, ModelMap modelMap) {
        ArticleModel resultModel = generalMapper.selectById(Integer.parseInt(id), ArticleModel.class);
        modelMap.put("model", resultModel);
        modelMap.put("news", generalMapper.selectPage(new Page<>(1, cacheContext.getCacheDTO().getSettingDTO().getDeskPageSize()), new EntityWrapper<>(new ArticleModel())));
        modelMap.put("title", resultModel.getTitle());
        modelMap.put("cache", cacheContext.getCacheDTO());
        return "desktop/default/article";
    }
}
