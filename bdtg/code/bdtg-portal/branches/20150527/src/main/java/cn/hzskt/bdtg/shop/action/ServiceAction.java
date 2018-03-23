package cn.hzskt.bdtg.shop.action;

import javax.servlet.http.HttpServletRequest;

import net.ryian.orm.domain.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.shop.domain.Service;
import cn.hzskt.bdtg.shop.service.ServiceService;
import cn.hzskt.bdtg.sys.domain.User;

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
    public String pub(HttpServletRequest request,Model model) {
		try {
			String step = request.getParameter("step");
			switch(step){
			case "1":
				Service o = bindEntity(request, entityClass);
				model.addAttribute("model", o);
				return getNameSpace() + "goods_pub_step_2";
			case "2":
				return getNameSpace() + "goods_pub_step_3";
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
