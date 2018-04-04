package net.ryian.example.sys.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import net.ryian.commons.CollectionUtils;
import net.ryian.core.SystemConfig;
import net.ryian.example.common.action.BaseMagicAction;
import net.ryian.example.sys.domain.Permission;
import net.ryian.example.sys.service.PermissionService;
import net.ryian.example.sys.service.ShiroDBRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/index")
public class IndexAction extends BaseMagicAction{

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private ShiroDBRealm shiroDBRealm;

	private void loadPermission() {
		if(shiroDBRealm.getAuthorizationInfo(this.getCurrentUser()) == null) {
			shiroDBRealm.clearCachedAuthorizationInfo(this.getCurrentUser());
			shiroDBRealm.isPermitted(SecurityUtils.getSubject().getPrincipals(), Long.toString(System.currentTimeMillis()));
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String execute(HttpServletRequest request) throws Exception {
		loadPermission();
		request.setAttribute("applicationName", SystemConfig.INSTANCE.getValue("application_name"));
		List<Permission> permissions = permissionService.getPermissionsByUser(getCurrentUser().id);
		int level = SystemConfig.INSTANCE.getValue("menu_level", 2);
		if(level == 2) {
			JSONObject o = new JSONObject();
			o.put("menus",getJSONArrayByPid(permissions,0l));
			request.setAttribute("menus", o.toJSONString());
		} else if(level == 3) {
			List<Permission> modules = getCurrentUser().permissions;
			request.setAttribute("modules", modules);
			if(!CollectionUtils.isEmpty(modules)) {
				request.setAttribute("menus", JSON.toJSONString(getCurrentUser().permissions.get(0)));
			} else {
				request.setAttribute("menus", "[]");
			}
		}
		request.setAttribute("level", level);
		request.setAttribute("username", getCurrentUser().getName());
		return "sys/index";
	}

	private JSONArray getJSONArrayByPid(List<Permission> permissions,Long pid) {
		JSONArray arr = new JSONArray();
		for(Permission permission : permissions) {
			if(pid.equals(permission.getPid())) {
				JSONObject o = JSON.parseObject(JSON.toJSONString(permission,new MenuFilter()));
				o.put("menus",getJSONArrayByPid(permissions,permission.getId()));
				arr.add(o);
			}
		}
		return arr;
	}

	public class MenuFilter extends AfterFilter {
		@Override
		public void writeAfter(Object o) {
			Permission permission = (Permission) o;
			writeKeyValue("menuId",permission.getId());
			writeKeyValue("menuname",permission.getName());
		}
	}


	
//	@RequestMapping(value = "getSubMenus",method = RequestMethod.POST)
//	public void getSubMenus(String moduleId,HttpServletRequest request,HttpServletResponse response) {
//		printJson(response,getMenus(moduleId).toString());
//	}
	
//	private JSONObject getMenus(String moduleId) {
//		JSONObject menusObj = new JSONObject();
//		JSONArray menusArr = new JSONArray();
//		List<ModuleBean> modules = MenuTreeUtil.getChildMenu(moduleId);
//		for(ModuleBean m : modules) {
//			JSONObject moduleObj = new JSONObject();
//			moduleObj.put("menuId", m.getId().toString());
//			moduleObj.put("icon", "icon-sys");
//			moduleObj.put("menuname", m.getName());
//			JSONArray subModuleArr = new JSONArray();
//			for(ModuleBean sm : MenuTreeUtil.getChildMenu(m.getId().toString())) {
//				JSONObject subModuleObj = new JSONObject();
//				subModuleObj.put("menuId", sm.getId().toString());
//				subModuleObj.put("icon", "icon-nav");
//				subModuleObj.put("menuname", sm.getName());
//				subModuleObj.put("url", sm.getUrl());
//				subModuleArr.add(subModuleObj);
//			}
//			moduleObj.put("menus", subModuleArr);
//			menusArr.add(moduleObj);
//		}
//		menusObj.put("menus", menusArr);
//		return menusObj;
//	}


	
	public String console() {
		return "console";
	}
	
}
