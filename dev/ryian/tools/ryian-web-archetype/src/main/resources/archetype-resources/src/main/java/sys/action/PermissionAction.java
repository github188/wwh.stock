#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.action;

        import com.alibaba.fastjson.JSON;
        import com.alibaba.fastjson.JSONArray;
        import com.alibaba.fastjson.JSONObject;
        import com.alibaba.fastjson.serializer.AfterFilter;
        import com.alibaba.fastjson.serializer.SerializeFilter;
        import net.ryian.commons.StringUtils;
        import net.ryian.core.SystemConfig;
        import net.ryian.core.domain.BaseEntity;
        import ${package}.common.action.MagicAction;
        import ${package}.sys.domain.Permission;
        import ${package}.sys.service.DictService;
        import ${package}.sys.service.PermissionService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.util.List;

/**
 * @description:Permission action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/permission")
@SuppressWarnings("serial")
public class PermissionAction extends MagicAction<Permission, PermissionService> {

    @Autowired
    private DictService dictService;

    @RequestMapping(value = "queryTreeGrid", method = RequestMethod.GET)
    public void queryTreeGrid(@RequestParam(value = "id", defaultValue = "0") String id, HttpServletResponse response) {
        List<Permission> permissionList = entityService.getPermissionsByPid(Long.valueOf(id));
        String jsonStr;
        if ("0".equals(id)) {
            Permission p = new Permission();
            p.setId(0l);
            p.setPid(-1l);
            p.setName(SystemConfig.INSTANCE.getValue("application_name"));
            JSONObject o = JSON.parseObject(JSON.toJSONString(p));
            JSONArray array = new JSONArray();
            o.put("children", JSON.parseArray(JSON.toJSONString(permissionList, new PermissionTreeGridFilter())));
            array.add(o);
            jsonStr = array.toJSONString();
        } else {
            jsonStr = JSON.toJSONString(permissionList, new PermissionTreeGridFilter());
        }
        printJson(response, jsonStr);
    }

    @RequestMapping(value = "queryRoleTree")
    public void queryRoleTree(HttpServletResponse response, @RequestParam(value = "roleId") String roleId) {
        List<Permission> permissions = entityService.getAll();
        List<Permission> rolePermissions = entityService.getPermissionsByRole(Long.valueOf(roleId));
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id", 0);
        obj.put("text", SystemConfig.INSTANCE.getValue("application_name"));
        obj.put("children", getJSONArrayByPid(permissions, 0l, new PermissionSelectFilter(rolePermissions)));
        arr.add(obj);
        printJson(response, arr.toJSONString());
    }

    @RequestMapping(value = "queryTree")
    public void queryRoleTree(HttpServletResponse response) {
        List<Permission> permissions = entityService.getAll();
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id", 0);
        obj.put("text", SystemConfig.INSTANCE.getValue("application_name"));
        obj.put("children", getJSONArrayByPid(permissions, 0l, null));
        arr.add(obj);
        printJson(response, arr.toJSONString());
    }

    @RequestMapping(value = "saveRolePermissions")
    public void saveRolePermissions(HttpServletResponse response, @RequestParam(value = "roleId") String roleId, @RequestParam(value = "permissions") String permissions) {
        entityService.saveRolePermissions(Long.valueOf(roleId), permissions, this.getCurrentUser().id);
        printJson(response, messageSuccuseWrap());
    }


    private JSONArray getJSONArrayByPid(List<Permission> permissions, Long pid, SerializeFilter filter) {
        JSONArray arr = new JSONArray();
        for (Permission permission : permissions) {
            if (pid.equals(permission.getPid())) {
                JSONObject o = JSON.parseObject(JSON.toJSONString(permission, filter));
                o.put("children", getJSONArrayByPid(permissions, permission.getId(), filter));
                arr.add(o);
            }
        }
        return arr;
    }

    @Override
    protected void beforeAdd(HttpServletRequest request, Model model) {
        model.addAttribute("pid", StringUtils.defaultString(request.getParameter("pid"), "0"));
        model.addAttribute("permissionTypes", dictService.getDictsByKey("permission_type").values());
    }

    @Override
    protected void beforeEdit(HttpServletRequest request, Model model, BaseEntity entity) {
        Permission permission = (Permission) entity;
        model.addAttribute("pid", permission.getPid());
        model.addAttribute("permissionTypes", dictService.getDictsByKey("permission_type").values());
    }

    public class PermissionTreeGridFilter extends AfterFilter {


        @Override
        public void writeAfter(Object o) {
            writeKeyValue("state", "closed");
        }
    }

    public class PermissionSelectFilter extends AfterFilter {

        private List<Permission> rolePermissions;

        public PermissionSelectFilter(List<Permission> rolePermissions) {
            this.rolePermissions = rolePermissions;
        }

        @Override
        public void writeAfter(Object o) {
            Permission permission = (Permission) o;
            if (rolePermissions.contains(permission)) {
                writeKeyValue("checked", true);
            }

        }
    }


}
