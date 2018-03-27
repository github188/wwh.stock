package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkEastQtz;
import cn.hzstk.securities.net.domain.PerformanceForecast;
import cn.hzstk.securities.net.service.PerformanceForecastService;
import cn.hzstk.securities.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:PerformanceForecast action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/performance-forecast")
@SuppressWarnings({"serial", "JavaDoc"})
public class PerformanceForecastAction extends MagicAction<PerformanceForecast,PerformanceForecastService> {

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            PerformanceForecast o = bindEntity(request, entityClass);
            StkEastQtz.obtainPerformanceForecast();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("currentDate", DateUtil.getMonthByType(Constant.QUARTER));
    }
}
