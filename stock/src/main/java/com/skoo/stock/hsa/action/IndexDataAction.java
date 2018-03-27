package com.skoo.stock.hsa.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockSummaryQtz;
import com.skoo.stock.hsa.domain.IndexData;
import com.skoo.stock.hsa.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:IndexData action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/index-data")
@SuppressWarnings("serial")
public class IndexDataAction extends ManAction<IndexData, IndexDataService> {

    @Autowired
    private StockSummaryQtz stockSummaryQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryId, HttpServletResponse response) {
        try {
            stockSummaryQtz.execute();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

}
