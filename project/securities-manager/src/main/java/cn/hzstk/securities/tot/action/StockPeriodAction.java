package cn.hzstk.securities.tot.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.tot.domain.StockPeriod;
import cn.hzstk.securities.tot.service.StockPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
*
* @description:StockPeriod action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/tot/stock-period")
@SuppressWarnings({"serial", "JavaDoc"})
public class StockPeriodAction extends MagicAction<StockPeriod,StockPeriodService> {
    @Autowired
    ParamService paramService;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Map<String,String> params = getParameterMap(request);
            entityService.selectStatPeriod(params);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("stockCode", paramService.getbyName(ParamKeys.RX_DEFAULT_CODE));
    }
}
