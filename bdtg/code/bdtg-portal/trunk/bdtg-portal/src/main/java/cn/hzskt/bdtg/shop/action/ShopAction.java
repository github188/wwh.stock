package cn.hzskt.bdtg.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzskt.bdtg.financial.domain.AuthBank;
import cn.hzskt.bdtg.financial.service.AuthBankService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.shop.domain.Service;
import cn.hzskt.bdtg.shop.service.ServiceService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.service.TaskService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.domain.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.shop.domain.Shop;
import cn.hzskt.bdtg.shop.service.ShopService;
import cn.hzskt.bdtg.sys.domain.User;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* @description:Shop action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/shop/shop")
@SuppressWarnings("serial")
public class ShopAction extends MagicAction<Shop,ShopService> {
	@Autowired
	ShopService shopService;
	@Autowired
	AuthSpaceService authSpaceService;
	@Autowired
	ServiceService serviceService;
	@Autowired
	TaskService taskService;
	@Autowired
	AuthBankService authBankService;
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		Map<String,String> params=getParameterMap(request);
		String view="";
		if(params.size()>0){
		 view=params.get("view");}
		User user = PortalUtil.getUser(request);
		BaseEntity entity = entityService.getByUid(user.getId()).get(0);
		Boolean bank = authBankService.getauthstatus(user.getId());
		List<Service> slist =serviceService.getBySI(entity.getId());
		model.addAttribute("slist",slist.size());
		int authbank=0;
		if(bank) authbank=1;
		PageInfo<?> task =taskService.selectMyTask(user.getId().toString(), null,1,20);
		JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(task, new DictFilter()));
			JSONArray row = o.getJSONArray("list");
		model.addAttribute("tlist",row.size());
		JSONObject style = JSON.parseObject(((Shop) entity).getShopBackstyle());
		model.addAttribute("authbank",authbank);
		if(null!=style) {
			model.addAttribute("repeat", style.get("r"));
			model.addAttribute("position", style.get("p"));
		}else {
			model.addAttribute("repeat", "0");
			model.addAttribute("position", "center top");
		}
		List<AuthSpace> list =authSpaceService.querybyuserId(user.getId());
		AuthSpace au=new AuthSpace();
		if(list.size()>0) {
			au= list.get(0);
		}
		model.addAttribute("model", entity);
		model.addAttribute("User",au);
		if(view.equals("task")) return task_index(model, row);
		else
		return goods_index(model, slist);
	}

	public String goods_index(Model model,List<Service> list){
		for(int i=0;i<list.size();i++){
			String pic= list.get(i).getPic();
			list.get(i).setPic(pic.split("\\|")[0]);
		}
		model.addAttribute("objs",list);
		return getNameSpace()+"index";
	}

	public String task_index(Model model,JSONArray row){
		List list=new ArrayList();
			for(int i=0;i<row.size();i++) {
				JSONObject obj = row.getJSONObject(i);
				Task ta = new Task();
				ta.setTaskCash(obj.getInteger("task_cash"));
				int view=0;
				int work=0;
				if(null!=obj.getInteger("view_num")) view=obj.getInteger("view_num");
				ta.setViewNum(view);
				if(null!=obj.getInteger("work_num")) work=obj.getInteger("work_num");
				ta.setWorkNum(work);
				ta.setTaskTitle(obj.getString("task_title"));
				ta.setTaskDesc(obj.getString("task_desc"));
				ta.setTaskStatus(obj.getString("task_status"));
				ta.setIndusPid(obj.getInteger("indus_pid"));
				list.add(ta);
			}
		model.addAttribute("objs",list);
		return getNameSpace()+"task_index";
	}

	 @RequestMapping(value = "manage")
	    public String manage(HttpServletRequest request,Model model)
	            throws Exception {
		 		User user = PortalUtil.getUser(request);
	            BaseEntity entity = entityService.getByUid(user.getId()).get(0);
		 		JSONObject style = JSON.parseObject(((Shop) entity).getShopBackstyle());
		 if(null!=style) {
			 model.addAttribute("repeat", style.get("r"));
			 model.addAttribute("position", style.get("p"));
		 }else {
			 model.addAttribute("repeat", "0");
			 model.addAttribute("position", "center top");
		 }
	            model.addAttribute("model", entity);
	            beforeEdit(request, model, entity);
	        return getNameSpace() + "shop_manage";
	    }

	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request,Model model)
			throws Exception {
		User user = PortalUtil.getUser(request);
		BaseEntity entity = entityService.getByUid(user.getId()).get(0);
		JSONObject style = JSON.parseObject(((Shop) entity).getShopBackstyle());
		if(null!=style) {
			model.addAttribute("repeat", style.get("r"));
			model.addAttribute("position", style.get("p"));
		}else {
			model.addAttribute("repeat", "0");
			model.addAttribute("position", "center top");
		}
		model.addAttribute("model", entity);
		beforeEdit(request,model,entity);
		return getNameSpace() + "edit";
	}

	@RequestMapping(value="shop_save")
	public void shopsave(HttpServletRequest request,Model model,HttpServletResponse response){
		try {
			Map param=getParameterMap(request);
			Shop shop =  bindEntity(request, Shop.class);
			User user = PortalUtil.getUser(request);
			List<Shop> base = shopService.getByUid(user.getId());
			JSONObject obj=null;
			String repeat="0";
			String style="";
			String position="center top";
			if(base.size()>0){
				Shop shop1=base.get(0);
				if(null!=shop.getBanner())
				{shop1.setBanner(shop.getBanner());}
				if(null!=shop.getShopBackground())
				{shop1.setShopBackground(shop.getShopBackground());}
				obj = JSON.parseObject(shop1.getShopBackstyle());
				if (null!=param.get("repeat")||null!=param.get("repeat1")) repeat="1";//"1"选中
				if(null!=param.get("position")) position =param.get("position").toString();
				else if(null!=param.get("position1")) position =param.get("position1").toString();
				if(null!=obj){
					obj.put("r",repeat);
					obj.put("p",position);
					style=obj.toJSONString();
				}else {
					style = "{" + "\"r\":\"" + repeat + "\"," + "\"p\":\"" + param.get("position") + "\"}";
				}

				shop1.setShopBackstyle(style);
				shopService.saveOrUpdate(shop1);
			}else {
				if (null != param.get("repeat")) repeat = "1";//"1"选中
					style = "{" + "\"r\":\"" + repeat + "\"," + "\"p\":\"" + param.get("position") + "\"}";
				shop.setShopBackstyle(style);
				shop.setUsername(user.getUserName());
				shop.setUid(user.getId().intValue());
				shopService.saveOrUpdate(shop);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("status", "success");
			data.put("data", "保存成功");
			String url = request.getScheme() +"://" + request.getServerName()
					+ ":" +request.getServerPort()+ "/shop/shop";
			data.put("url",url);
			response.getWriter().write(JSON.toJSONString(data));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value="clean")
	public void clean(HttpServletRequest request,Model model,HttpServletResponse response){
		try {
			User user = PortalUtil.getUser(request);
			List<Shop> base = shopService.getByUid(user.getId());
			if(base.size()>0){
				Shop shop =base.get(0);
				shop.setBanner("");
				shop.setShopBackground("");
				shop.setShopBackstyle("");
				shopService.saveOrUpdate(shop);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("status", "success");
			data.put("data", "已清除");
			String url = request.getScheme() +"://" + request.getServerName()
					+ ":" +request.getServerPort()+ "/shop/shop";
			data.put("url", url);
			response.getWriter().write(JSON.toJSONString(data));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
