package com.skoo.stock.sys.action;

import com.skoo.common.SystemConfig;
import com.skoo.core.utils.JsonUtils;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.domain.Sysmenu;
import com.skoo.stock.sys.service.SysmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:Category action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysmenu")
public class SysmenuAction extends ManAction<Sysmenu, SysmenuService> {

    @Autowired
    private SysmenuService sysmenuService;

    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Sysmenu o = bindEntity(request, entityClass);
            String title = OperateLog.OPERATING_UPDATE_TITLE;
            if (o.getId() == null) {
                title = OperateLog.OPERATING_INSERT_TITLE;
            }
            entityService.saveOrUpdate(o);
            String content = "id=" + o.getId();
            operateLogService.wirteLog(request, title, content);
            sysmenuService.removeSysmenuForRole();//通知清除缓存
            printText(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printText(response, messageFailureWrap("保存失败！"));
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                entityService.logicRemove(Long.parseLong(id));
                String content = "id=" + id;
                operateLogService.wirteLog(request, OperateLog.OPERATING_DELETE_TITLE, content);
            }
            sysmenuService.removeSysmenuForRole();//通知清除缓存
            printText(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printText(response, messageFailureWrap("删除失败！"));
        }
    }

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
