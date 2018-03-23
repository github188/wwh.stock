package cn.hzskt.bdtg.sys.action;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.sys.domain.Role;
import cn.hzskt.bdtg.sys.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:Role action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/role")
@SuppressWarnings("serial")
public class RoleAction extends MagicAction<Role, RoleService> {

    @RequestMapping(value = "queryAll",method = RequestMethod.GET)
    public void queryAll(HttpServletResponse response,@RequestParam(value = "userId")String userId) throws Exception {
        try {
            List<Role> roleList = entityService.getAll();
            List<Role> userRoles = entityService.getRolesByUser(Long.valueOf(userId));
            JSONObject o = new JSONObject();
            o.put("rows",JSON.parseArray(JSON.toJSONString(roleList, new SelectRolesJSONFilter(userRoles))));
            printJson(response, o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "selectPermissions")
    public String selectPermissions(@RequestParam(value = "roleId")String roleId,Model model) {
        model.addAttribute("roleId",Long.valueOf(roleId));
        return "/sys/role/selectPermissions";
    }

    public class SelectRolesJSONFilter extends AfterFilter {

        private List<Role> userRoles;

        public SelectRolesJSONFilter(List<Role> userRoles) {
            this.userRoles = userRoles;
        }

        @Override
        public void writeAfter(Object o) {
            Role role = (Role) o;
            if(userRoles.contains(role)) {
                writeKeyValue("checked",true);
            }
        }
    }

    @RequestMapping(value = "saveUserRoles")
    public void saveUserRoles(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="userId")String userId, @RequestParam(value="roles")String roles) {
        entityService.saveUserRoles(Long.valueOf(userId),roles,this.getCurrentUser(request).id);
        printJson(response,this.messageSuccuseWrap());
    }

}
