package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkCommQtz;
import cn.hzstk.securities.stk.domain.InvestCalendar;
import cn.hzstk.securities.stk.service.InvestCalendarService;
import net.ryian.commons.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
*
* @description:InvestCalendar action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/invest-calendar")
@SuppressWarnings({"serial", "JavaDoc"})
public class InvestCalendarAction extends MagicAction<InvestCalendar,InvestCalendarService> {

    @Autowired
    private StkCommQtz stkCommQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            InvestCalendar o = bindEntity(request, entityClass);
            String dt = o.getDt();
            stkCommQtz.executeInvest(dt);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("currentDate", DateUtils.format(new Date()));
    }
}
