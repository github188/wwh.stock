package cn.hzskt.bdtg.shop.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.shop.domain.Shop;
import cn.hzskt.bdtg.shop.service.ShopService;

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


}
