package com.skoo.stock.sys.action;

import com.skoo.common.SystemConfig;
import com.skoo.core.utils.JsonUtils;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.Sysmenu;
import com.skoo.stock.sys.service.SysmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:Category action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/rolemenu")
public class RoleMenuAction extends ManAction<Sysmenu, SysmenuService> {

    @Autowired
    private SysmenuService sysmenuService;

    /**
     * 菜单树构建
     */
    @RequestMapping(value = "/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);

            Map<String, String> psdInfo = condition.getMap();
            if (psdInfo.get("id") == null) {
                List<Sysmenu> list = new ArrayList<Sysmenu>();
                list.add(getRoot());
                printJson(response, JsonUtils.bean2JsonArray(list));
                return;
            } else {
                psdInfo.put("upId", psdInfo.get("id"));
            }
            List<Sysmenu> result = sysmenuService.getSqlSession().selectList("Sysmenu_tree_list", psdInfo);
            printJson(response, JsonUtils.bean2JsonArray(result));
        } catch (Exception e) {
            logger.error("菜单数据获取失败", e);
        }
    }

    /**
     * 菜单树构建
     */
    @RequestMapping(value = "/cachetree")
    public void cachetree(HttpServletRequest request, HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);

            Map<String, String> psdInfo = condition.getMap();
            if (psdInfo.get("id") == null) {
                List<Sysmenu> list = new ArrayList<Sysmenu>();
                list.add(getRoot());
                printJson(response, JsonUtils.bean2JsonArray(list));
                return;
            } else {
                psdInfo.put("upId", psdInfo.get("id"));
            }

            List<Sysmenu> result = sysmenuService.getMenuList(psdInfo);
            printJson(response, JsonUtils.bean2JsonArray(result));
        } catch (Exception e) {
            logger.error("菜单数据获取失败", e);
        }
    }

    @RequestMapping(value = "/selfAdd")
    public String selfAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        if (id != null)
            if ("-1".equals(id)) {
                model.addAttribute("model", getRoot());
            } else {
                model.addAttribute("model", entityService.get(Long.valueOf(id)));
            }
        return super.add();
    }

    /**
     * 获取root
     *
     * @return
     */
    private Sysmenu getRoot() {
        Sysmenu c = new Sysmenu();
        c.setMenuName(SystemConfig.INSTANCE.getValue("orgname")+"后台管理菜单");
        c.setMenuCode("root");
        c.setId(-1L);
        c.setUpId(0L);
        return c;
    }

    /**
     * 校验重名
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/validate")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> para = bindMap(request);
        Integer count = sysmenuService.getSqlSession().selectOne("Sysmenu_tree_count", para);
        if (count != 0) {
            printText(response, "false");
        }
        printText(response, "true");
    }

}
