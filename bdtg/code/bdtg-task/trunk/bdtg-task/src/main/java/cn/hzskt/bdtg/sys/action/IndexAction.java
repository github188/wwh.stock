package cn.hzskt.bdtg.sys.action;

import cn.hzskt.bdtg.sys.domain.Permission;
import cn.hzskt.bdtg.sys.service.PermissionService;
import cn.hzskt.bdtg.sys.service.ShiroDBRealm;
import cn.hzskt.bdtg.common.action.BaseMagicAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import net.ryian.commons.CollectionUtils;
import net.ryian.core.SystemConfig;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/index")
public class IndexAction extends BaseMagicAction{

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private ShiroDBRealm shiroDBRealm;

	private void loadPermission(HttpServletRequest request) {
		if(shiroDBRealm.getAuthorizationInfo(this.getCurrentUser(request)) == null) {
			shiroDBRealm.clearCachedAuthorizationInfo(this.getCurrentUser(request));
			shiroDBRealm.isPermitted(SecurityUtils.getSubject().getPrincipals(), Long.toString(System.currentTimeMillis()));
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String execute(HttpServletRequest request) throws Exception {
		loadPermission(request);
		request.setAttribute("applicationName", SystemConfig.INSTANCE.getValue("application_name"));
		List<Permission> permissions = permissionService.getPermissionsByUser(getCurrentUser(request).id);
		int level = SystemConfig.INSTANCE.getValue("menu_level", 2);
		if(level == 2) {
			JSONObject o = new JSONObject();
			o.put("menus",getJSONArrayByPid(permissions,0l));
			request.setAttribute("menus", o.toJSONString());
		} else if(level == 3) {
			List<Permission> menus = getMenus(permissions);
			request.setAttribute("menus",menus);
		}
		request.setAttribute("level", level);
		request.setAttribute("username", getCurrentUser(request).getName());
		return "sys/index";
	}

	private List<Permission> getMenus(List<Permission> permissions) {
		List<Permission> menus = new ArrayList<>();
		for(Permission menu : permissions) {
			if(menu.getPid()==0l) {
				List<Permission> subMenus = new ArrayList<>();
				for(Permission subMenu : permissions) {
					if(subMenu.getPid().equals(menu.getId())) {
						List<Permission> subMenus1 = new ArrayList<>();
						for(Permission subMenu1 : permissions) {
							if(subMenu1.getPid().equals(subMenu.getId())) {
								subMenus1.add(subMenu1);
							}
						}
						subMenu.setChildren(subMenus1);
						subMenus.add(subMenu);
					}
				}
				menu.setChildren(subMenus);
				menus.add(menu);
			}
		}
		Collections.sort(menus, new Comparator<Permission>() {
			@Override
			public int compare(Permission o1, Permission o2) {
				return 0;
			}
		});
		return menus;
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
