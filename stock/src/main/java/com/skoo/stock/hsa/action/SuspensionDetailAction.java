package com.skoo.stock.hsa.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockPlateQtz;
import com.skoo.stock.hsa.domain.SuspensionDetail;
import com.skoo.stock.hsa.service.SuspensionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:SuspensionDetail action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/suspension-detail")
@SuppressWarnings("serial")
public class SuspensionDetailAction extends ManAction<SuspensionDetail, SuspensionDetailService> {

    @Autowired
    private StockPlateQtz stockPlateQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletResponse response) {
        try {
            stockPlateQtz.setSuspension();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

}
