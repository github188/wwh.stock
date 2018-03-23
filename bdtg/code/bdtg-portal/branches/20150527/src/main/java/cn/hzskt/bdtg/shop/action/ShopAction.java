package cn.hzskt.bdtg.shop.action;

import javax.servlet.http.HttpServletRequest;

import net.ryian.orm.domain.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.shop.domain.Shop;
import cn.hzskt.bdtg.shop.service.ShopService;
import cn.hzskt.bdtg.sys.domain.User;

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

	 @RequestMapping(value = "manage")
	    public String edit(HttpServletRequest request,Model model)
	            throws Exception {
		 		User user = PortalUtil.getUser(request);
	            BaseEntity entity = entityService.get(user.getId());
	            model.addAttribute("model", entity);
	            beforeEdit(request,model,entity);
	        return getNameSpace() + "shop_manage";
	    }
}
