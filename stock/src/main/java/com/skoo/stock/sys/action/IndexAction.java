package com.skoo.stock.sys.action;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.stock.common.action.BaseManAction;
import com.skoo.stock.sys.domain.ModuleBean;
import com.skoo.stock.util.MenuTreeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sys/index")
public class IndexAction extends BaseManAction {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView execute(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sys/index");
        mv.addObject("applicationName",
                SystemConfig.INSTANCE.getValue("application_name"));
        int level = SystemConfig.INSTANCE.getValue("menu_level", 2);
        mv.addObject("menus", getMenus("-1", request).toString());
        mv.addObject("multiTab", SystemConfig.INSTANCE.getValue("multiTab"));
//        if (level == 2) {
//            mv.addObject("menus", getMenus(null).toString());
//        } else if (level == 3) {
//            List<ModuleBean> modules = MenuTreeUtil.getChildMenu(null);
//            mv.addObject("modules", modules);
//            if (!modules.isEmpty()) {
//                mv.addObject("menus",
//                        getMenus(modules.get(0).getId().toString()).toString());
//            } else {
//                mv.addObject("menus", "[]");
//            }
//        }
//        mv.addObject("level", level);
//        mv.addObject("username", UserContext.get(Constant.CURRENT_USERNAME));
        return mv;
    }

    @RequestMapping(value = "getSubMenus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSubMenus(String moduleId, HttpServletRequest request,
                                  HttpServletResponse response) {
        return getMenus(moduleId, request);
    }

    private JSONObject getMenus(String moduleId, HttpServletRequest request) {
        JSONObject menusObj = new JSONObject();
        JSONArray menusArr = new JSONArray();

        List<ModuleBean> modules = MenuTreeUtil.getChildMenu(moduleId, request);
        for (ModuleBean m : modules) {
            JSONObject moduleObj = new JSONObject();
            moduleObj.put("menuId", m.getId().toString());
            String iconPapa = StringUtils.isNullOrEmpty(m.getIcon()) ? "icon-sys" : m.getIcon();
            moduleObj.put("icon", iconPapa);
            moduleObj.put("menuname", m.getName());
            JSONArray subModuleArr = new JSONArray();
            String target = "";
            int icount = 0;
            for (ModuleBean sm : MenuTreeUtil
                    .getChildMenu(m.getId().toString(), request)) {
                JSONObject subModuleObj = new JSONObject();
                subModuleObj.put("menuId", sm.getId().toString());
                String iconson = StringUtils.isNullOrEmpty(sm.getIcon()) ? "icon-nav" : sm.getIcon();
                subModuleObj.put("icon", iconson);
                subModuleObj.put("menuname", sm.getName());
                subModuleObj.put("url", sm.getUrl());
                subModuleArr.add(subModuleObj);
                target = target + (icount == 0 ? "" : ",") + sm.getId();
                icount++;
            }
            moduleObj.put("target", target);
            moduleObj.put("menus", subModuleArr);
            menusArr.add(moduleObj);
        }
        menusObj.put("menus", menusArr);
        return menusObj;
    }

}
