package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.service.StkCommQtz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.net.domain.ProfitReport;
import cn.hzstk.securities.net.service.ProfitReportService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:ProfitReport action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/profit-report")
@SuppressWarnings("serial")
public class ProfitReportAction extends MagicAction<ProfitReport,ProfitReportService> {

    @Autowired
    private StkCommQtz stkCommQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            ProfitReport o = bindEntity(request, entityClass);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
