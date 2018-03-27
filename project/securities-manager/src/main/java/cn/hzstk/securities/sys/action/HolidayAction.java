package cn.hzstk.securities.sys.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.sys.domain.Holiday;
import cn.hzstk.securities.sys.service.HolidayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
*
* @description:Holiday action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/sys/holiday")
@SuppressWarnings({"serial", "JavaDoc"})
public class HolidayAction extends MagicAction<Holiday,HolidayService> {
}
