package com.skoo.stock.common.service;

import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.HistoryData;
import com.skoo.stock.hsa.domain.HistoryDetail;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.service.HistoryDataService;
import com.skoo.stock.hsa.service.HistoryDetailService;
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
public class StockInfoQtz {
    private static final Logger LOG = LoggerFactory.getLogger(Constant.LOGFILE);

    protected final static MarketDataService mService = SpringContextUtil.getBean(MarketDataService.class);
    protected final static HistoryDataService hService = SpringContextUtil.getBean(HistoryDataService.class);
    protected final static HistoryDetailService hdService = SpringContextUtil.getBean(HistoryDetailService.class);

    public void execute() {

        try {
            String[] val;
            LinkedHashMap<String, String[]> map = StockUtil.getStockList("stock", null);
            for (int i = 1; i < map.size(); i++) {
                val = map.get(String.valueOf(i));
                getMarketData(val);
            }

            boolean loopFlag = true;
            LinkedHashMap<String, String[]> map1;
            String sAddr;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int k = 0; k < listName.size(); k++) {
                        sAddr = listName.get(k);
                        if (sAddr.indexOf("<") >= 0) sAddr = sAddr.split("<")[1].replace(">","");
                        map1 = StockUtil.getStockList("stock", sAddr);
                        for (int i = 1; i < map1.size(); i++) {
                            val = map1.get(String.valueOf(i));
                            getMarketData(val);
                        }
                    }
                }
            } while (loopFlag);

            detail_set(true);

            LOG.info("调度完成");
        } catch (Exception ex) {
            LOG.error("[StockInfoQtz]： "+ex.getCause());
        }
    }

    private void getMarketData(String[] val) {
        MarketData o = new MarketData();
        o.setCode(val[0]);
        o.setName(val[1]);
        o.setLatestPrice(StockUtil.cvtDouble(val[2]));
        o.setUdWidth(StockUtil.cvtDouble(val[3].replace("%", "")));
        o.setUdAmount(StockUtil.cvtDouble(val[4]));
        o.setFiveWidth(val[5].replace("%", ""));
        o.setVolume(StockUtil.cvtDouble(val[6]));
        o.setTurnVolume(StockUtil.cvtDouble(val[7]));
        o.setTurnoverRate(val[8].replace("%", ""));
        o.setAmplitude(val[9]);
        o.setVolumeRatio(StockUtil.cvtDouble(val[10]));
        o.setCommittee(StockUtil.cvtDouble(val[11]));
        o.setPeRatio(StockUtil.cvtDouble(val[12]));
        o.setNetFlag("1");
        mService.saveOrUpdate(o);
    }

    public static void detail_set(boolean flag) {
        String[] val;
        String sAddr;

        List<MarketData> list = mService.getAll();
        for (int i = 0; i < list.size(); i++) {
            val = null;
            while (val == null || StringUtils.isEmpty(val[10])) val = StockUtil.getStockDyn(list.get(i).getCode());
            getHistoryDetail(val,flag);
        }
        boolean loopFlag = true;
        do {
            List<String> listName = FileUtil.readStockFile();
            if (listName.size() == 0) {
                loopFlag = false;
            } else {
                for (int k = 0; k < listName.size(); k++) {
                    sAddr = listName.get(k);
                    if (sAddr.indexOf("<") < 0) continue;
                    sAddr = sAddr.split("<")[1].replace(">","");
                    val = StockUtil.getStockDyn(sAddr);
                    if (val == null || StringUtils.isEmpty(val[10])) return;
                    getHistoryDetail(val,flag);
                }
            }
        } while (loopFlag);
    }

    private static void getHistoryDetail(String[] val, boolean flag) {
        HistoryDetail o = new HistoryDetail();
/*
        List<HistoryData> list = hService.getHistoryDetinfo(param[0]);
        if (list.get(0) != null) {
            o.setHighestPrice(list.get(0).getHighestPrice());
            o.setLowestPrice(list.get(0).getLowestPrice());
            o.setPressurePrice(list.get(0).getHighestPrice());
            list = hService.getHistoryDt(val[0], val[10]);
            if (list.size()>0 && list.get(0) != null) {
                o.setSupportPrice(list.get(0).getClosingPrice());
            }

            list = hService.getHistoryDetinfo1(val[0]);
            if (list.get(0) != null) {
                o.setSuspensionDays(list.get(0).getSuspensionDays());
                o.setStartDate(list.get(0).getStartDate());
                o.setEndDate(list.get(0).getEndDate());
            }
        }
*/

        o.setCode(val[0]);
        o.setName(val[1]);
        if (StockUtil.cvtDouble(val[2])==0) return;
        o.setCurrentPrice(StockUtil.cvtDouble(val[2]));
        o.setFiveWidth(StockUtil.cvtDouble(val[6]));
        o.setTenWidth(StockUtil.cvtDouble(val[7]));
        o.setTwentyWidth(StockUtil.cvtDouble(val[8]));
        o.setMemo(val[9]);

        hdService.saveOrUpdate(o);
        if (flag) setHistoryData(val);
    }

    private static void setHistoryData(String[] val) {
        double zg = StockUtil.cvtDouble(val[4]);
        if (zg != 0) {
            double zd = StockUtil.cvtDouble(val[5]);
            String zf = String.format("%.2f", StockUtil.calcWidth(val[3],val[4],val[5]));
            HistoryData oh = new HistoryData();
            oh.setCode(val[0]);
            oh.setDt(val[10]);
            oh.setHighestPrice(zg);
            oh.setLowestPrice(zd);
            oh.setAmplitude(zf);

            hService.saveOrUpdate(oh);
        }

    }
}
