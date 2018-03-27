package cn.hzstk.securities.shop.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ryian.orm.domain.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.shop.domain.Service;
import cn.hzstk.securities.shop.service.ServiceService;

/**
*
* @description:Service action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/shop/service")
@SuppressWarnings("serial")
public class ServiceAction extends MagicAction<Service,ServiceService> {
	
	 @RequestMapping(value = "goodslist")
	    public String goodslist(HttpServletRequest request,Model model)
	            throws Exception {
//		 		User user = PortalUtil.getUser(request);
//	            BaseEntity entity = entityService.get(user.getId());
//	            model.addAttribute("model", entity);
//	            beforeEdit(request,model,entity);
	        return getNameSpace() + "goodslist";
	    }
	 
	@RequestMapping(value = "goodspub")
    public String add(HttpServletRequest request,Model model) {
        beforeAdd(request,model);
        return getNameSpace() + "goods_pub_step_1";
    }
	
	 @RequestMapping(value = "info/{id}")
	    public String info(HttpServletRequest request,@PathVariable("id") Long id, Model model)
	            throws Exception {
	        if (id != null) {
	            BaseEntity entity = entityService.get(id);
	            model.addAttribute("model", entity);
	            beforeEdit(request,model,entity);
	        }
	        return getNameSpace() + "goods_info";
	    }
	 
	 @RequestMapping(value = "buygoods/{id}")
	    public String buygoods(HttpServletRequest request,@PathVariable("id") Long id, Model model)
	            throws Exception {
	        if (id != null) {
	            BaseEntity entity = entityService.get(id);
	            model.addAttribute("model", entity);
	            beforeEdit(request,model,entity);
	        }
	        return getNameSpace() + "buy_goods_step_1";
	    }
	 
	 
	@RequestMapping(value = "pub")
    public void pub(HttpServletRequest request,Model model,HttpServletResponse response) {
		try {
			Service o = bindEntity(request, entityClass);
			o.setServiceStatus(0);

			//TODO 店铺id
			entityService.saveOrUpdate(o);
				
			JSONObject obj = new JSONObject();
			  Map<String, Object> data = new HashMap<String, Object>();
	            data.put("status", "success");
	            data.put("data", "保存成功");
	            data.put("url", "/shop/service/pub_accomplish");
	            response.getWriter().write(JSON.toJSONString(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping(value = "pub_accomplish")
    public String pub_accomplish(HttpServletRequest request,Model model) {
        return getNameSpace() + "pub_accomplish";
    }
	
	@RequestMapping(value = "order")
    public String order(HttpServletRequest request,Model model) {
		try {
			String step = request.getParameter("step");
			switch(step){
			case "1":
				Service o = bindEntity(request, entityClass);
				model.addAttribute("model", o);
				return getNameSpace() + "buy_goods_step_2";
			case "2":
				return getNameSpace() + "buy_goods_step_3";
			case "3":
				return getNameSpace() + "buy_goods_step_4";
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
