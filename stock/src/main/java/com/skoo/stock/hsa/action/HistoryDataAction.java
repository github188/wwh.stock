package com.skoo.stock.hsa.action;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.SpringQtz;
import com.skoo.stock.hsa.domain.HistoryData;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.service.HistoryDataService;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.StockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:HistoryData action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/history-data")
@SuppressWarnings("serial")
public class HistoryDataAction extends ManAction<HistoryData, HistoryDataService> {

    @Autowired
    private SpringQtz springQtz;
    @Autowired
    protected MarketDataService mService;

    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String dt, HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(dt)) {
                springQtz.execute();
            } else {
                List<MarketData> list = mService.getAll();
                for (int i = 0; i < list.size(); i++) {
                    getHistoryData(list.get(i).getCode(), dt);
                }

                boolean loopFlag = true;
                do {
                    List<String> listName = FileUtil.readStockFile();
                    if (listName.size() == 0) {
                        loopFlag = false;
                    } else {
                        for (int i = 0; i < listName.size(); i++) {
                            getHistoryData(listName.get(i), dt);
                        }
                    }
                } while (loopFlag);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void getHistoryData(String code,String dt) {
        if (code.length() > 6) {
            String[] cdSplt = code.split("&date=");
            if (cdSplt.length <= 1) return;
            code = cdSplt[0].split("=")[1].substring(2, 8);
            dt = cdSplt[1].substring(0, 10);
        }
        String[] val = StockUtil.getDataHist(code, dt);
        if (val == null || StringUtils.isEmpty(val[0])) return;
        HistoryData o = new HistoryData();
        o.setCode(code);
        o.setDt(dt);
        o.setClosingPrice(StockUtil.cvtDouble(val[0]));
        o.setUdWidth(val[1].replace("%", ""));

        double zs = StockUtil.cvtDouble(val[2]);
        double zg = StockUtil.cvtDouble(val[4]);
        double zd = StockUtil.cvtDouble(val[5]);

        o.setHighestPrice(zg);
        o.setLowestPrice(zd);
        o.setAmplitude(String.format("%.2f", (zg - zd) / zs * 100));
        o.setVolume(Math.floor(StockUtil.cvtDouble(val[6])) / 100);
        o.setTurnVolume(Math.floor(StockUtil.cvtDouble(val[7]) / 1000) / 100);
        entityService.saveOrUpdate(o);
    }

    private void getHistoryData1(String code) {
        String sAddr = "&pageid=1|17";
        if (code.trim().length() > 6) {
            sAddr = code.split("<")[1].split("&")[0] + sAddr;
            code = sAddr.split("code=")[1].split("&")[0];
        } else {
            sAddr = SystemConfig.INSTANCE.getValue("historyAddress") + code + sAddr;
            List<HistoryData> list = entityService.getHistoryInfo(code);
            if (list != null && list.size() > 1 && list.get(1).getOrderBy() == null) {
                String dt = list.get(1).getDt();
                sAddr = sAddr + ":" + dt;
            }
        }
        LinkedHashMap<String, String[]> map = StockUtil.getStockList(code, sAddr);
        if (map == null || map.size() <= 1) return;
        String[] val;
        for (int i = map.size() - 1; i > 0; i--) {
            val = map.get(String.valueOf(i));

            HistoryData o = setHistData(code, val);
            entityService.saveOrUpdate(o);
        }
/*
  List<HistoryData> list = entityService.getHistoryInfo(code);
  String maxdt="";
  if (list != null && list.size() > 0) {
   maxdt = list.get(0).getDt();
  }
  String dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
  int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  if (hour <= 15 && dt.equals(maxdt) || maxdt.compareTo(dt) < 0) {
   val = StockUtil.getStockCur(code);
   HistoryData o = setHistData(code, val);
   entityService.saveOrUpdate(o);
  }
*/
    }

    private HistoryData setHistData(String code, String[] val) {
        HistoryData o = new HistoryData();
        o.setCode(code);
        o.setDt(val[0]);
        o.setClosingPrice(StockUtil.cvtDouble(val[1]));
        o.setUdWidth(val[2]);
        o.setTurnoverRate(val[3]);
        o.setVolume(StockUtil.cvtDouble(val[4]));
        o.setTurnVolume(StockUtil.cvtDouble(val[5]));
        o.setInflowFund(StockUtil.cvtDouble(val[6]));
        o.setTradeBalance(StockUtil.cvtDouble(val[7]));
        o.setFundDiff(StockUtil.cvtDouble(val[8]));
        o.setNetInflowRate(val[9]);
        return o;
    }

}
