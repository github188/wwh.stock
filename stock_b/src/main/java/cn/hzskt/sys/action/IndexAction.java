package cn.hzskt.sys.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zjhc.cas.clientX.bean.ModuleBean;
import com.zjhc.cas.clientX.ehcache.UserContext;
import com.zjhc.cas.clientX.util.Constant;
import com.zjhc.cas.clientX.util.MenuTreeUtil;
import com.zjhcsoft.smartcity.magic.common.SystemConfig;
import cn.hzskt.common.action.BaseMagicAction;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/index")
public class IndexAction extends BaseMagicAction{

	
	@RequestMapping(method = RequestMethod.GET)
	public String execute(HttpServletRequest request) throws Exception {
		request.setAttribute("applicationName", SystemConfig.INSTANCE.getValue("application_name"));
		int level = SystemConfig.INSTANCE.getValue("menu_level", 2);
		if(level == 2) {
			request.setAttribute("menus", getMenus(null).toString());
		} else if(level == 3) {
			List<ModuleBean> modules = MenuTreeUtil.getChildMenu(null);
			request.setAttribute("modules", modules);
			if(!modules.isEmpty()) {
				request.setAttribute("menus", getMenus(modules.get(0).getId().toString()).toString());
			} else {
				request.setAttribute("menus", "[]");
			}
		}
		request.setAttribute("level", level);
		request.setAttribute("username", UserContext.get(Constant.CURRENT_USERNAME));
		return "sys/index";
	}
	
	@RequestMapping(value = "getSubMenus",method = RequestMethod.POST)
	public void getSubMenus(String moduleId,HttpServletRequest request,HttpServletResponse response) {
		printJson(response,getMenus(moduleId).toString());
	}
	
	private JSONObject getMenus(String moduleId) {
		JSONObject menusObj = new JSONObject();
		JSONArray menusArr = new JSONArray();
		List<ModuleBean> modules = MenuTreeUtil.getChildMenu(moduleId);
		for(ModuleBean m : modules) {
			JSONObject moduleObj = new JSONObject();
			moduleObj.put("menuId", m.getId().toString());
			moduleObj.put("icon", "icon-sys");
			moduleObj.put("menuname", m.getName());
			JSONArray subModuleArr = new JSONArray();
			for(ModuleBean sm : MenuTreeUtil.getChildMenu(m.getId().toString())) {
				JSONObject subModuleObj = new JSONObject();
				subModuleObj.put("menuId", sm.getId().toString());
				subModuleObj.put("icon", "icon-nav");
				subModuleObj.put("menuname", sm.getName());
				subModuleObj.put("url", sm.getUrl());
				subModuleArr.add(subModuleObj);
			}
			moduleObj.put("menus", subModuleArr);
			menusArr.add(moduleObj);
		}
		menusObj.put("menus", menusArr);
		return menusObj;
	}
	
	public String console() {
		return "console";
	}
	
}
