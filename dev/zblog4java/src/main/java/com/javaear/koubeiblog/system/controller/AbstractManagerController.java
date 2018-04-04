package com.javaear.koubeiblog.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.javaear.koubeiblog.CacheController;
import com.javaear.koubeiblog.system.entity.BaseModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aooer
 */
@RequestMapping("system/management")
public abstract class AbstractManagerController<T extends BaseModel> extends CacheController {

    protected static final String MNG_URL = "redirect:%s-management.html";
    protected static final String EDT_URL = "system/edit/%s-edit";
    protected static final String ADD_URL = "system/add/%s-add";

    protected final Class MODEL_CLASS = modelClass();//模块class
    protected final String MODEL_NAME = modelName(); //模块name

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 默认分页page查询
     *
     * @param modelMap modelMap
     */
    public void pageQuery(ModelMap modelMap) {
        try {
            pageQuery(modelMap, new EntityWrapper<>(MODEL_CLASS.newInstance()));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用分页page查询
     *
     * @param modelMap modelMap
     */
    public void pageQuery(ModelMap modelMap, EntityWrapper entityWrapper) {
        int pageNo = 1;
        String pageNoStr = request.getParameter("pageNo");
        if (StringUtils.isNotBlank(pageNoStr) && StringUtils.isNumeric(pageNoStr))
            pageNo = Integer.parseInt(pageNoStr);
        if (pageNo < 1)
            pageNo = 1;
        Page page = new Page<>(pageNo, cacheContext.getCacheDTO().getSettingDTO().getSystemPageSize());
        modelMap.put("models", generalMapper.selectPage(page, entityWrapper));
        modelMap.put("page", page);
    }

    /**
     * 通用删除方法
     *
     * @return Str
     */
    protected String removeById() {
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id))
            generalMapper.deleteById(Integer.parseInt(id), MODEL_CLASS);
        return String.format(MNG_URL, MODEL_NAME);
    }

    private String modelName() {
        return modelClass().getSimpleName().toLowerCase().split("model")[0];
    }

    private Class modelClass() {
        return (Class) ((ParameterizedTypeImpl) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
