package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.net.domain.StockGroup;
import cn.hzstk.securities.net.service.StockGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
*
* @description:StockGroup action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/stock-group")
@SuppressWarnings("serial")
public class StockGroupAction extends MagicAction<StockGroup,StockGroupService> {

}
