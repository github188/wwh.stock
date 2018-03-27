package com.skoo.stock.hsa.action;

import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockIndexQtz;
import com.skoo.stock.common.service.StockInfoQtz;
import com.skoo.stock.common.service.StockPlateQtz;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.domain.PlateStock;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.hsa.service.PlateStockService;
import com.skoo.stock.util.StockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @description:MarketData action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/market-data")
@SuppressWarnings("serial")
public class MarketDataAction extends ManAction<MarketData,MarketDataService> {

    @Autowired
    private StockInfoQtz stockInfoQtz;
    @Autowired
    private StockIndexQtz stockIndexQtz;
    @Autowired
    protected PlateStockService pService;

    @Autowired
    private StockPlateQtz stockPlateQtz;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryId, HttpServletResponse response) {
        try {
            /*String[] val;
            if(StringUtils.isEmpty(industryId)) {
                LinkedHashMap<String, String[]> map = StockUtil.getStockList("stock", null);
                for (int i = 1; i < map.size(); i++) {
                    val = map.get(String.valueOf(i));
                    getMarketData(val);
                }
            } else {
                List<PlateStock> list = pService.getPlateInfo(industryId);
                for (int i = 0; i < list.size(); i++) {
                    val = StockUtil.getStockCur(list.get(i).getCode());
                    if (val == null) continue;
                    getMarketData(val);
                }
            }*/
            if ("1".equals(Constant.NETFLG)) {
                stockInfoQtz.execute();
            } else if ("2".equals(Constant.NETFLG)) {
                stockPlateQtz.setMarket();
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    private void getMarketData(String[] val) {
        MarketData o = new MarketData();
        o.setCode(val[0]);
        o.setName(val[1]);
        o.setLatestPrice(StockUtil.cvtDouble(val[2]));
        o.setUdWidth(StockUtil.cvtDouble(val[3].replace("%", "")));
        o.setUdAmount(StockUtil.cvtDouble(val[4]));
        o.setFiveWidth(val[5]);
        o.setVolume(StockUtil.cvtDouble(val[6]));
        o.setTurnVolume(StockUtil.cvtDouble(val[7]));
        o.setTurnoverRate(val[8]);
        o.setAmplitude(val[9]);
        o.setVolumeRatio(StockUtil.cvtDouble(val[10]));
        o.setCommittee(StockUtil.cvtDouble(val[11]));
        o.setPeRatio(StockUtil.cvtDouble(val[12]));
        entityService.saveOrUpdate(o);
    }

    @RequestMapping(value = "dataLoad", method = RequestMethod.POST)
    public void dataLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String[] val;
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                val = StockUtil.getStockCur(id);
                if (val == null) continue;
                getMarketData(val);
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataLoad", e);
            printJson(response, messageFailureWrap("重新加载失败！"));
        }
    }

    @RequestMapping(value = "dataAdd", method = RequestMethod.POST)
    public void dataAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                PlateStock o = new PlateStock();
                o.setConceptId(Constant.CONCEPTFLG);
                o.setIndustryType(Constant.INDUSTRYFLG);
                o.setNetFlag(Constant.NETFLG);
                o.setCode(id);
                pService.delOrUpdate(o);
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataAdd", e);
            printJson(response, messageFailureWrap("加入自选失败！"));
        }
    }

    @RequestMapping(value = "rqWidth", method = RequestMethod.POST)
    public void rqWidth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            stockPlateQtz.sp_rqWidth();
            //stockIndexQtz.def_set("4");
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("rqWidth", e);
            printJson(response, messageFailureWrap("板块日期失败！"));
        }
    }

}
