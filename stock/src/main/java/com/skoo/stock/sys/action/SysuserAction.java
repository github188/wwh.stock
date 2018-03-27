package com.skoo.stock.sys.action;

import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.mmb.service.MemberService;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.domain.Sysuser;
import com.skoo.stock.sys.service.SysuserService;
import com.skoo.stock.sys.utils.json.JsonUtils;
import com.skoo.stock.util.ManUtil;
import com.skoo.stock.util.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:Sysuser action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysuser")
@SuppressWarnings("serial")
public class SysuserAction extends ManAction<Sysuser, SysuserService> {

    @Autowired
    private MemberService memberService;

    /**
     * 分页查询Dictionary列表.
     */
    @RequestMapping(value = "queryPaged")
    @SuppressWarnings("unchecked")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<Sysuser> page = entityService.queryPaged(condition);
            List<Sysuser> list = page.getRows();
            Map map = new HashMap();
            map.put("list", list);
            List<Sysuser> listFinal = entityService.selectUserWithRole(map);
            page.setRows(listFinal);

            printText(response, JsonUtils.bean2Json(page, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("queryPaged", e);
        }
    }

    /**
     * 根据角色获取用户
     */
    @RequestMapping(value = "/roleusers")
    public void getRoleUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<Map> page = entityService.queryRoleUsers(condition);
            printText(response, JsonUtils.bean2Json(page, null, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("角色用户获取失败", e);
        }
    }

    /**
     * 根据用户获取角色
     */
    @RequestMapping(value = "/userroles")
    public void getUserRoles(HttpServletRequest request, HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<Map> page = entityService.queryUserRoles(condition);
            printText(response, JsonUtils.bean2Json(page, null, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("角色用户获取失败", e);
        }
    }

    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Sysuser o = bindEntity(request, entityClass);
            Condition condition = bindCondition(request);
            String password = o.getPassword();
            if (StringUtils.isEmpty(EncryptUtil.decrypt(password))) {
                o.setPassword(EncryptUtil.encrypt(password));
            }
            String title = OperateLog.OPERATING_UPDATE_TITLE;
            if (o.getId() == null) {
                title = OperateLog.OPERATING_INSERT_TITLE;
            }

            entityService.saveOrUpdate(o);

            entityService.processRoleExt(o, condition);
            String content = "id=" + o.getId();
            operateLogService.wirteLog(request, title, content);
            printText(response, messageSuccuseWrap());
        } catch (DuplicateKeyException e) {
            logger.error("save", e);
            printText(response, messageFailureWrap("保存失败！(用户编号重复)"));
        } catch (Exception e) {
            logger.error("save", e);
            printText(response, messageFailureWrap("保存失败！"));
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String ids = request.getParameter("ids");
            String iflag = "1";
            int icount = 0;
            for (String id : ids.split(",")) {
                if (!ManUtil.getAuthConfig(Constant.AUTH_ADMIN).equals(id)) { //管理员用户不能删除
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
                    printText(response, messageFailureWrap("删除部分成功！(超级管理员账户无法删除)"));
                } else {
                    printText(response, messageFailureWrap("超级管理员账户无法删除！"));
                }
            }
        } catch (Exception e) {
            logger.error("delete", e);
            printText(response, messageFailureWrap("删除失败！"));
        }
    }

    @RequestMapping("chgpass")
    public String chgpass(HttpServletRequest req, HttpServletResponse res, Model model) {
        String uuid = EncryptUtil.encrypt(UserSession.getUserId().toString());
        model.addAttribute("uuid", uuid);
        return getNameSpace() + "chgpass";
    }

    @RequestMapping("dochgpass")
    public void dochgpass(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        try {
            Condition condition = bindCondition(req);
            String userId = EncryptUtil.decrypt(condition.get("uuid"));
            String oldpass = condition.get("oldpass");
            String newpass = condition.get("newpass");
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            if (!StringUtils.isEmpty(oldpass)) {
                map.put("oldpass", EncryptUtil.encrypt(oldpass));
            } else {
                map.put("oldpass", "");
            }
            map.put("password", EncryptUtil.encrypt(newpass));

            int checkCount;
            if (Constant.USER_TYPE_STAFF.equals(UserSession.getUserType())) {
                checkCount = entityService.selectpass(map);
            } else {
                checkCount = memberService.selectpass(map);
            }
            if (checkCount == 1) {
                if (Constant.USER_TYPE_STAFF.equals(UserSession.getUserType())) {
                    entityService.changepass(map);
                } else {
                    memberService.changepass(map);
                }
                printText(res, messageSuccuseWrap());
            } else {
                printText(res, messageFailureWrap("原密码不正确！"));
            }
        } catch (Exception e) {
            logger.error("dochgpass", e);
            printText(res, messageFailureWrap("密码修改失败！"));
        }
    }
}
