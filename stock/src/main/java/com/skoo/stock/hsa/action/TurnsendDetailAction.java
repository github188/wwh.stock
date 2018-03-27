package com.skoo.stock.hsa.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockPlateQtz;
import com.skoo.stock.hsa.domain.TurnsendDetail;
import com.skoo.stock.hsa.service.TurnsendDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:TurnsendDetail action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/turnsend-detail")
@SuppressWarnings("serial")
public class TurnsendDetailAction extends ManAction<TurnsendDetail, TurnsendDetailService> {

    @Autowired
    private StockPlateQtz stockPlateQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletResponse response) {
        try {
            stockPlateQtz.setTurnsend();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

}
