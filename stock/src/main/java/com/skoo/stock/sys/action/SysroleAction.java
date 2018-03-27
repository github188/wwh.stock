package com.skoo.stock.sys.action;

import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.domain.Sysrole;
import com.skoo.stock.sys.service.SysmenuService;
import com.skoo.stock.sys.service.SysroleMenuService;
import com.skoo.stock.sys.service.SysroleService;
import com.skoo.stock.util.ManUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:Sysrole action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysrole")
@SuppressWarnings("serial")
public class SysroleAction extends ManAction<Sysrole, SysroleService> {
    @Autowired
    private SysroleMenuService sysroleMenuService;

    @Autowired
    private SysmenuService sysmenuService;


    @RequestMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            model.addAttribute("model", entityService.get(id));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("roleId", id);
        List<String> dbList = sysroleMenuService.selectIdList(map);
        model.addAttribute("ids", dbList);

        return getNameSpace() + "edit";
    }

    /**
     * 保存记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Sysrole o = bindEntity(request, entityClass);
            Condition condition = bindCondition(request);
            String title = OperateLog.OPERATING_UPDATE_TITLE;
            if (o.getId() == null) {
                title = OperateLog.OPERATING_INSERT_TITLE;
            }

            entityService.saveOrUpdate(o);
            // 处理角色相关事务
            entityService.processRoleExt(o, condition);

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
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String ids = request.getParameter("ids");
            String iflag = "1";
            int icount = 0;
            for (String id : ids.split(",")) {
                if (!ManUtil.getAuthConfig(Constant.AUTH_ADMIN_ROLE).equals(id)) { //管理员角色不能删除
                    icount++;
                    entityService.logicRemove(Long.parseLong(id));
                    String content = "id=" + id;
                    operateLogService.wirteLog(request, OperateLog.OPERATING_DELETE_TITLE, content);
                } else {
                    iflag = "0";
                }
            }
            if ("1".equals(iflag)) {
                printText(response, messageSuccuseWrap());
            } else {
                if (icount > 0) {
                    printText(response, messageFailureWrap("删除部分成功！(超级管理员角色无法删除)"));
                } else {
                    printText(response, messageFailureWrap("超级管理员角色无法删除！"));
                }
            }
        } catch (Exception e) {
            logger.error("delete", e);
            printText(response, messageFailureWrap("删除失败！"));
        }
    }

}
