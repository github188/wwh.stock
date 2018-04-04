package com.javaear.koubeiblog.system.controller.manager;

import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.model.CategoryModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aooer
 */
@Controller
public class CategoryManagerController extends AbstractManagerController<CategoryModel> {

    @RequestMapping("category-management")
    public void list(ModelMap modelMap) {
        pageQuery(modelMap);
        modelMap.put("title", "分类管理");
    }

    @RequestMapping("category-add")
    public String add(ModelMap modelMap) {
        String type = request.getParameter("type");
        CategoryModel categoryModel = RequestUtils.copyProperties(request, CategoryModel.class);
        if ((!StringUtils.isEmpty(type) && "show".equals(type)) || categoryModel == null) {
            modelMap.put("title", "新建分类");
            return String.format(ADD_URL, MODEL_NAME);
        }
        categoryModel.check();
        return String.format(generalMapper.insertSelective(categoryModel) > 0 ? MNG_URL : ADD_URL, MODEL_NAME);
    }

    @RequestMapping("category-edit")
    public String edit(ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id))
            return String.format(MNG_URL, MODEL_NAME);
        String type = request.getParameter("type");
        CategoryModel categoryModel = RequestUtils.copyProperties(request, CategoryModel.class);
        if ((StringUtils.isNotBlank(type) && "show".equals(type)) || categoryModel == null) {
            modelMap.put("model", generalMapper.selectById(Integer.parseInt(id), CategoryModel.class));
            modelMap.put("title", "编辑分类");
            return String.format(EDT_URL, MODEL_NAME);
        }
        return String.format(generalMapper.updateSelectiveById(categoryModel) > 0 ? MNG_URL : EDT_URL, MODEL_NAME);
    }

    @RequestMapping("category-remove")
    public String remove() {
        return removeById();
    }

}
