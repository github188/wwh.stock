package com.skoo.stock.hsa.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.domain.StockBasic;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.hsa.service.StockBasicService;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.StockUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:StockBasic action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/stock-basic")
@SuppressWarnings("serial")
public class StockBasicAction extends ManAction<StockBasic, StockBasicService> {

    @Autowired
    protected MarketDataService mService;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String code, HttpServletResponse response) {
        try {
            String[] stockdata;
            if (StringUtils.isEmpty(code)) {
                List<MarketData> list = mService.getStockInfo();
                for (int i = 0; i < list.size(); i++) {
                    stockdata = StockUtil.getStockBasic(list.get(i).getCode());
                    getHistoryDetail(stockdata);
                }
            } else {
                stockdata = StockUtil.getStockBasic(code);
                getHistoryDetail(stockdata);
            }

            boolean loopFlag = true;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int k = 0; k < listName.size(); k++) {
                        stockdata = StockUtil.getStockBasic(listName.get(k));
                        getHistoryDetail(stockdata);
                    }
                }
            } while (loopFlag);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void getHistoryDetail(String[] val) {
        if (val == null) return;
        StockBasic o = new StockBasic();
        o.setCode(val[0]);
        o.setName(val[1]);
        o.setCirculationEquity(StockUtil.cvtDouble(val[2]));
        o.setTotalEquity(StockUtil.cvtDouble(val[3]));
        o.setPerProfit(StockUtil.cvtDouble(val[4]));
        o.setNetAssets(StockUtil.cvtDouble(val[5]));
        o.setNdistribProfit(StockUtil.cvtDouble(val[6]));
        o.setCapitalFund(StockUtil.cvtDouble(val[7]));
        o.setAssetsYield(StockUtil.cvtDouble(val[8]));
        o.setMainRevenue(StockUtil.cvtDouble(val[9]));
        o.setNetProfit(StockUtil.cvtDouble(val[10]));
        o.setProfitDescribe(StockUtil.cutTrim(val[11], 50));
        o.setMainBusiness(StockUtil.cutTrim(val[12], 50));
        o.setThePlate(StockUtil.cutTrim(val[13], 50));
        //o.setMemo(StockUtil.cutTrim(val[15],50));

        entityService.saveOrUpdate(o);
    }

}
