package com.skoo.stock.sys.action;

import com.skoo.common.SystemConfig;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.domain.TreeNode;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.domain.Sysdept;
import com.skoo.stock.sys.domain.Sysmenu;
import com.skoo.stock.sys.service.SysdeptService;
import com.skoo.stock.sys.utils.json.JsonUtils;
import com.skoo.orm.service.support.query.Condition;
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
import java.util.TreeMap;

/**
 * @description:Sysdept action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysdept")
@SuppressWarnings("serial")
public class SysdeptAction extends ManAction<Sysdept, SysdeptService> {

    /**
     * 保存部门记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Sysdept o = bindEntity(request, entityClass);
            String title = OperateLog.OPERATING_UPDATE_TITLE;
            if (o.getId() == null) {
                title = OperateLog.OPERATING_INSERT_TITLE;
            }
            entityService.saveOrUpdate(o);
            String content = "id=" + o.getId();
            operateLogService.wirteLog(request, title, content);
            //entityService.removeSysmenuForRole();//通知清除缓存
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
            //entityService.removeSysmenuForRole();//通知清除缓存
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
                List<Sysdept> list = new ArrayList<>();
                list.add(getRoot());
                printJson(response, JsonUtils.bean2JsonArray(list));
                return;
            } else {
                psdInfo.put("upId", psdInfo.get("id"));
            }
            List<Sysmenu> result = entityService.manQuery("Sysdept_tree_list", psdInfo);
            printJson(response, JsonUtils.bean2JsonArray(result, "yyyy-MM-dd", Sysdept.class));
        } catch (Exception e) {
            logger.error("部门数据获取失败", e);
        }
    }

    /**
     * 部门树构建
     */
    @RequestMapping(value = "/treeAll")
    public void treeAll(HttpServletRequest request, HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            Map<String, String> psdInfo = condition.getMap();

            Long rootId = -1l;
            Object objId = psdInfo.get("id");
            if (objId != null) {
                rootId = Long.parseLong(objId.toString());
            }

            List<Map> fileList = entityService.manQuery("Sysdept_tree_all", psdInfo);

            TreeNode root = new TreeNode();
            Map<Long, TreeNode> map = new TreeMap<>();
            List<TreeNode> list = new ArrayList<>();
            TreeNode tempTree;
            //3 将list中元素放入map.其中主键为key.
            TreeNode subRoot = null;
            for (Map lstMap : fileList) {
                tempTree = new TreeNode();
                tempTree.setId(Long.parseLong(lstMap.get("id").toString()));
                tempTree.setPid(Long.parseLong(lstMap.get("up_id").toString()));
                tempTree.setText(lstMap.get("dept_name").toString());
                map.put(tempTree.getId(), tempTree);
                list.add(tempTree);

                if (tempTree.getId() == rootId) {
                    root = tempTree;
                }
            }

            //4 循环。将循环中的节点，添加到上一级节点中。
            for (TreeNode treeNode : list) {
                tempTree = treeNode;
                TreeNode t = map.get(tempTree.getPid());
                if (t != null) {
                    t.getChildren().add(tempTree);
                    map.put(tempTree.getPid(), t);
                }
            }

            printJson(response, JsonUtils.bean2JsonArray(root));
        } catch (Exception e) {
            logger.error("部门数据获取失败", e);
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
    private Sysdept getRoot() {
        Sysdept c = new Sysdept();
        c.setDeptName(SystemConfig.INSTANCE.getValue("orgname"));
        c.setDeptCode("root");
        c.setDeptType("88");//总公司
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
        Integer count = entityService.getSqlSession().selectOne("Sysdept_tree_count", para);
        if (count != 0) {
            printText(response, "false");
        }
        printText(response, "true");
    }
}
