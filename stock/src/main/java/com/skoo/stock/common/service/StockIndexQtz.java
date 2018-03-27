package com.skoo.stock.common.service;

import com.skoo.common.SystemConfig;
import com.skoo.commons.DateUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.IndexData;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.hsa.domain.PlateStock;
import com.skoo.stock.hsa.service.IndexDataService;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.hsa.service.PlateStockService;
import com.skoo.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class StockIndexQtz {
    private static final Logger LOG = LoggerFactory.getLogger("mytest");

    protected final static MarketDataService mService = SpringContextUtil.getBean(MarketDataService.class);
    protected final static PlateStockService pService = SpringContextUtil.getBean(PlateStockService.class);
    protected final static IndexDataService iService = SpringContextUtil.getBean(IndexDataService.class);

    public void execute() {

        try {
            index_set();

            String[] val;
            List<PlateStock> listStock = pService.getPlateInfo(Constant.CONCEPTFLG);
            for (int i = 0; i < listStock.size(); i++) {
                val = StockUtil.getStockCur(listStock.get(i).getCode());
                if (val == null) continue;
                getMarketData(val);
            }
        } catch (Exception ex) {
            LOG.error("[StockIndexQtz]： "+ex.getMessage());
        }
    }

    public static void def_set(String industry_id) {
        String[] val;
        List<PlateStock> listStock = pService.getPlateInfo(industry_id);
        for (int i = 0; i < listStock.size(); i++) {
            val = StockUtil.getStockCur(listStock.get(i).getCode());
            if (val == null) continue;
            getMarketData(val);
        }
    }

    public static void index_set() {
        String code = "sh000001,sz399001,sz399006,sh000016,sh000300,sh000905";
        List<String[]> list = StockUtil.getStockList(code);
        String[] val = code.split(",");
        for (int i = 0; i < list.size(); i++) {
            IndexData o = setInexData(val[i], list.get(i));
            iService.saveOrUpdate(o);
        }
        LinkedHashMap<String, String[]> map = StockUtil.getStockList("index", null);
        if (map.size() == 0) map = StockUtil.getStockList("index", null);
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            if (StockUtil.cvtDouble(val[9]) != 0) {
                IndexData o = setInexData(val);
                if (o == null) continue;
                iService.saveOrUpdate(o);
            }
        }
    }

    private static IndexData setInexData(String code,String[] val) {
        IndexData o = new IndexData();
        o.setCode(code);
        o.setName(val[0]);
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setStartPrice(StockUtil.cvtDouble(val[1]));

        double zs = StockUtil.cvtDouble(val[2]);
        double dq = StockUtil.cvtDouble(val[3]);
        o.setEndPrice(zs);
        o.setClosingPrice(dq);
        o.setUdAmount(StockUtil.subWidth(dq, zs));
        o.setUdWidth(String.format("%.2f",StockUtil.calcWidth(dq,zs,zs)));
        o.setHighestPrice(StockUtil.cvtDouble(val[4]));
        o.setLowestPrice(StockUtil.cvtDouble(val[5]));
        o.setVolume(Math.floor(StockUtil.cvtDouble(val[8]) / 100));
        o.setTurnVolume(Math.floor(StockUtil.cvtDouble(val[9])/10000));
        return o;
    }

    private static IndexData setInexData(String[] val) {
        String name = "当月";
        if (val[0].indexOf(name) < 0) return null;
        IndexData o = new IndexData();
        o.setCode(val[0].replace(name, "C"));
        o.setName(val[0]);
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setStartPrice(StockUtil.cvtDouble(val[5]));
        o.setEndPrice(StockUtil.cvtDouble(val[4]));
        o.setClosingPrice(StockUtil.cvtDouble(val[1]));
        o.setUdAmount(StockUtil.cvtDouble(val[2]));
        o.setUdWidth(val[3].replace("%", ""));
        o.setHighestPrice(StockUtil.cvtDouble(val[6]));
        o.setLowestPrice(StockUtil.cvtDouble(val[7]));
        o.setVolume(StockUtil.cvtDouble(val[8]));
        o.setTurnVolume(StockUtil.cvtDouble(val[9]));
        return o;
    }

    private static void getMarketData(String[] val) {
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
        o.setNetFlag(Constant.EASTFLG);
        mService.saveOrUpdate(o);
    }
}
