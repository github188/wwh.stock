package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkCommQtz;
import cn.hzstk.securities.stk.domain.PlateStock;
import cn.hzstk.securities.stk.service.PlateStockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:PlateStock action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/plate-stock")
@SuppressWarnings({"serial", "JavaDoc"})
public class PlateStockAction extends MagicAction<PlateStock,PlateStockService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    @Autowired
    private StkCommQtz stkCommQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            PlateStock o = bindEntity(request, entityClass);
            String plateCode = o.getPlateCode();
            stkCommQtz.executeBs(plateCode);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
