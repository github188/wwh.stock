package com.skoo.stock.hsa.action;

import com.skoo.stock.common.service.StockInfoQtz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.hsa.domain.HistoryDetail;
import com.skoo.stock.hsa.service.HistoryDetailService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:HistoryDetail action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/history-detail")
@SuppressWarnings("serial")
public class HistoryDetailAction extends ManAction<HistoryDetail, HistoryDetailService> {

    @Autowired
    private StockInfoQtz stockInfoQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryId, HttpServletResponse response) {
        try {
            stockInfoQtz.detail_set(false);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

}
