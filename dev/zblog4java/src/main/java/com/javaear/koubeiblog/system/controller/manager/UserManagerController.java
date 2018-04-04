package com.javaear.koubeiblog.system.controller.manager;

import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aooer
 */
@Controller
public class UserManagerController extends AbstractManagerController<UserModel> {

    @RequestMapping("user-management")
    public void list(ModelMap modelMap) {
        pageQuery(modelMap);
        modelMap.put("title", "用户管理");
    }

    @RequestMapping("user-add")
    public String add(ModelMap modelMap) {
        String type = request.getParameter("type");
        UserModel userModel = RequestUtils.copyProperties(request, UserModel.class);
        if ((!StringUtils.isEmpty(type) && "show".equals(type)) || userModel == null) {
            modelMap.put("title", "新建用户");
            return String.format(ADD_URL, MODEL_NAME);
        }
        userModel.check();
        return String.format(generalMapper.insertSelective(userModel) > 0 ? MNG_URL : ADD_URL, MODEL_NAME);
    }

    @RequestMapping("user-edit")
    public String edit(ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id))
            return String.format(MNG_URL, MODEL_NAME);
        String type = request.getParameter("type");
        UserModel userModel = RequestUtils.copyProperties(request, UserModel.class);
        if ((StringUtils.isNotBlank(type) && "show".equals(type)) || userModel == null) {
            modelMap.put("model", generalMapper.selectById(Integer.parseInt(id), UserModel.class));
            modelMap.put("title", "编辑用户");
            return String.format(EDT_URL, MODEL_NAME);
        }
        return String.format(generalMapper.updateSelectiveById(userModel) > 0 ? MNG_URL : EDT_URL, MODEL_NAME);
    }

    @RequestMapping("user-remove")
    public String remove() {
        return removeById();
    }
}
