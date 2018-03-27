package com.skoo.stock.common.service;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.hsa.domain.HistoryData;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.service.HistoryDataService;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class SpringQtz {
    private static final Logger LOG = LoggerFactory.getLogger("mytest");

    protected MarketDataService mService = SpringContextUtil.getBean(MarketDataService.class);
    protected HistoryDataService hService = SpringContextUtil.getBean(HistoryDataService.class);
    private String maxdt;

    public void execute() {

        try {
            maxdt = hService.getHistoryMaxDt();
            List<MarketData> list = mService.getAll();
            for (int i = 0; i < list.size(); i++) {
                getHistoryData(list.get(i).getCode());
            }

            boolean loopFlag = true;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int i = 0; i < listName.size(); i++) {
                        getHistoryData(listName.get(i));
                    }
                }
            } while (loopFlag);

            LOG.info("调度完成");
        } catch (Throwable e) {
            LOG.error("[SpringQtz]： "+e.getMessage());
        }
    }

    private void getHistoryData(String code) {
        String sAddr = "&pageid=1|17";
        if (code.trim().length()>6) {
            if (code.indexOf("code=") < 0) return;
            sAddr = code.split("<")[1].split("&")[0] + sAddr;
            code = sAddr.split("code=")[1].split("&")[0];
        } else {
            sAddr = SystemConfig.INSTANCE.getValue("historyAddress") + code + sAddr + ":" + maxdt;
        }
        LinkedHashMap<String, String[]> map = StockUtil.getStockList(code, sAddr);
        if (map == null || map.size() <= 1) return;
        String[] val;
        //String dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
        for (int i = map.size() - 1; i > 0; i--) {
            val = map.get(String.valueOf(i));

            HistoryData o = setHistData(code, val);
/*
            if (dt.equals(o.getDt())) {
                String[] stkVal = StockUtil.getStockValue(code);
                double zs = StockUtil.cvtDouble(stkVal[2]);
                double zg = StockUtil.cvtDouble(stkVal[4]);
                double zd = StockUtil.cvtDouble(stkVal[5]);
                String zf = String.format("%.2f", (zg - zd) / zs * 100);
                o.setHighestPrice(zg);
                o.setLowestPrice(zd);
                o.setAmplitude(zf);
            }
*/
            hService.saveOrUpdate(o);
        }
    }

    private HistoryData setHistData(String code,String[] val) {
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
