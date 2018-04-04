package com.javaear.koubeiblog.system.controller.manager;

import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.model.TagModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aooer
 */
@Controller
public class TagManagerController extends AbstractManagerController<TagModel> {

    @RequestMapping("tag-management")
    public void list(ModelMap modelMap) {
        pageQuery(modelMap);
        modelMap.put("title", "标签管理");
    }

    @RequestMapping("tag-add")
    public String add(ModelMap modelMap) {
        String type = request.getParameter("type");
        TagModel tagModel = RequestUtils.copyProperties(request, TagModel.class);
        if ((!StringUtils.isEmpty(type) && "show".equals(type)) || tagModel == null) {
            modelMap.put("title", "新建标签");
            return String.format(ADD_URL, MODEL_NAME);
        }
        tagModel.check();
        return String.format(generalMapper.insertSelective(tagModel) > 0 ? MNG_URL : ADD_URL, MODEL_NAME);
    }

    @RequestMapping("tag-edit")
    public String edit(ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id))
            return String.format(MNG_URL, MODEL_NAME);
        String type = request.getParameter("type");
        TagModel tagModel = RequestUtils.copyProperties(request, TagModel.class);
        if ((StringUtils.isNotBlank(type) && "show".equals(type)) || tagModel == null) {
            modelMap.put("model", generalMapper.selectById(Integer.parseInt(id), TagModel.class));
            modelMap.put("title", "编辑标签");
            return String.format(EDT_URL, MODEL_NAME);
        }
        return String.format(generalMapper.updateSelectiveById(tagModel) > 0 ? MNG_URL : EDT_URL, MODEL_NAME);
    }

    @RequestMapping("tag-remove")
    public String remove() {
        return removeById();
    }
}
