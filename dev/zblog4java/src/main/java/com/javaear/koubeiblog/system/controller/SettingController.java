package com.javaear.koubeiblog.system.controller;

import com.alibaba.fastjson.JSON;
import com.javaear.koubeiblog.CacheController;
import com.javaear.koubeiblog.GlobalConstants;
import com.javaear.koubeiblog.system.entity.dto.SettingDTO;
import com.javaear.koubeiblog.system.entity.model.ConfigModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("system")
public class SettingController extends CacheController {


    @RequestMapping(value = "setting", method = RequestMethod.GET)
    public void viewSetting(ModelMap modelMap) {
        modelMap.put("title", "网站设置");
    }

    @RequestMapping(value = "setting", method = RequestMethod.POST)
    public void saveSetting(HttpServletRequest request, ModelMap modelMap) {
        SettingDTO settingDTO = RequestUtils.copyProperties(request, SettingDTO.class);
        generalMapper.updateSelectiveById(new ConfigModel(GlobalConstants.ConfigSettingId, JSON.toJSONString(settingDTO)));
        cacheContext.clearCacheDTO();
        modelMap.put("settingDTO", settingDTO);
        modelMap.put("title", "网站设置");
    }
}
