package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkCommQtz;
import cn.hzstk.securities.stk.domain.HqDetails;
import cn.hzstk.securities.stk.service.HqDetailsService;
import net.ryian.commons.DateUtils;
import org.apache.log4j.Logger;
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
* @description:HqDetails action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/hq-details")
@SuppressWarnings({"serial", "JavaDoc"})
public class HqDetailsAction extends MagicAction<HqDetails,HqDetailsService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    @Autowired
    private StkCommQtz stkCommQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            HqDetails o = bindEntity(request, entityClass);
            String dt = o.getDt();
            stkCommQtz.execute(dt);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("currentDate", DateUtils.format(new Date()));
    }
}
