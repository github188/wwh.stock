package com.skoo.stock.hsa.action;

import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.hsa.domain.PlateStock;
import com.skoo.stock.hsa.domain.StockNew;
import com.skoo.stock.hsa.service.PlateStockService;
import com.skoo.stock.hsa.service.StockNewService;
import com.skoo.stock.util.HtmlUnitUtil;
import com.skoo.stock.util.StockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:StockNew action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/stock-new")
@SuppressWarnings("serial")
public class StockNewAction extends ManAction<StockNew, StockNewService> {
    @Autowired
    protected PlateStockService pService;

    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String code, HttpServletResponse response) {
        try {
            String[] val;
            List<PlateStock> listStock = pService.getPlateInfo(Constant.CONCEPTFLG);
            for (int i = 0; i < listStock.size(); i++) {
                val = HtmlUnitUtil.getEastValue(listStock.get(i).getCode());
                if (val == null) continue;
                setStockNew(val);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void setStockNew(String[] val) {
        StockNew o = new StockNew();
        o.setCode(val[0]);
        o.setName(val[1]);
        o.setLatestPrice(val[2]);
        o.setAveragePrice(val[3]);
        o.setUdWidth(val[4]);
        o.setUdAmount(val[5]);
        o.setVolume(val[6]);
        o.setTurnVolume(val[7]);
        o.setTurnoverRate(val[8]);
        o.setVolumeRatio(val[9]);
        //o.setVolumeRatio(StockUtil.cvtDouble(val[10]));
        //o.setCommittee(StockUtil.cvtDouble(val[11]));
        //o.setPeRatio(StockUtil.cvtDouble(val[12]));
        //o.setFiveWidth(val[5]);
        o.setNetFlag(Constant.NETFLG);
        o.setHighestPrice(val[10]);
        o.setLowestPrice(val[11]);
        o.setTodayPrice(val[12]);
        o.setYesterdayPrice(val[13]);
        o.setMaxPrice(val[14]);
        o.setMinPrice(val[15]);
        o.setOutsideDish(val[16]);
        o.setInsideDish(val[17]);
        o.setInflowFund(val[20]);
        o.setInflowLarge(val[28]);
        o.setInflowBig(val[32]);
        entityService.saveOrUpdate(o);
    }
}
