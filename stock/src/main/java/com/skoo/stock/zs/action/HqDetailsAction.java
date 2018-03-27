package com.skoo.stock.zs.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockZsQtz;
import com.skoo.stock.zs.domain.HqDetails;
import com.skoo.stock.zs.service.HqDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @description:HqDetails action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/zs/hq-details")
@SuppressWarnings("serial")
public class HqDetailsAction extends ManAction<HqDetails,HqDetailsService> {

    @Autowired
    private StockZsQtz stockZsQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String dt, HttpServletResponse response) {
        try {
            stockZsQtz.execute();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
