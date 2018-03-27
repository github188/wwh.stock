package cn.hzstk.securities.tot.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.tot.domain.StockWidth;
import cn.hzstk.securities.tot.service.StockWidthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
*
* @description:StockWidth action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/tot/stock-width")
@SuppressWarnings("serial")
public class StockWidthAction extends MagicAction<StockWidth,StockWidthService> {

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Map<String,String> params = getParameterMap(request);
            entityService.query(params);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
