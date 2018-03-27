package com.skoo.stock.common.service;

import com.skoo.commons.DateUtils;
import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.*;
import com.skoo.stock.hsa.service.IndustryDataService;
import com.skoo.stock.hsa.service.MarketDataService;
import com.skoo.stock.hsa.service.SuspensionDetailService;
import com.skoo.stock.hsa.service.TurnsendDetailService;
import com.skoo.stock.util.HtmlUnitUtil;
import com.skoo.stock.util.JsoupUtil;
import com.skoo.stock.util.beanutil.ConvertFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class StockPlateQtz {
    private static final Logger LOG = LoggerFactory.getLogger(Constant.LOGFILE);

    protected final static MarketDataService mService = SpringContextUtil.getBean(MarketDataService.class);
    protected final static IndustryDataService iService = SpringContextUtil.getBean(IndustryDataService.class);
    protected final static TurnsendDetailService tService = SpringContextUtil.getBean(TurnsendDetailService.class);
    protected final static SuspensionDetailService sService = SpringContextUtil.getBean(SuspensionDetailService.class);
    //protected final static PlateStockService pService = SpringContextUtil.getBean(PlateStockService.class);

    public void execute() {

        try {
            setPlate("2");
            setMarket();
            sp_rqWidth();

            /*String[] val;
            List<PlateStock> listStock = pService.getPlateInfo("4");
            for (int i = 0; i < listStock.size(); i++) {
                val = JsoupUtil.getStockCur(listStock.get(i).getCode());
                if (val == null) continue;
                getMarketData(val);
            }*/
        } catch (Exception ex) {
            LOG.error("["+this.getClass()+"]： "+ex.getCause());
        }
    }

    public static void setPlate(String industryType) {
        String[] val;
        String[] arg = new String[]{"#gnmore", "#bklist"};
        if ("1".equals(industryType)) {
        }
        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getEastList(arg);
        //if (map.size() == 0) map = HtmlUnitUtil.getEastList(arg);
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            IndustryData o = setIndustryData(industryType, val);
            if (o == null) continue;
            iService.saveOrUpdate(o);
        }
    }

    private static IndustryData setIndustryData(String industryType, String[] val) {
        IndustryData o = new IndustryData();
        o.setIndustryId(val[0]);
        o.setIndustryName(val[1]);
        //o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setUdWidth(val[3]);
        if (StringUtils.isNotEmpty(val[4])) o.setCircuValue(JsoupUtil.cvtDouble(val[4]));
        o.setTurnoverRate(val[5]);
        o.setUpNum(Integer.valueOf(val[6]));
        o.setDownNum(Integer.valueOf(val[7]));
        o.setStockName(val[8]);
        o.setStockWidth(val[9]);
        o.setNetAddress(Constant.EASTADDR + val[11]);
        o.setNetFlag(Constant.NETFLG);
        o.setIndustryType(industryType);
        o.setFundAddress(val[12]);
        o.setStockAddress(val[18]);
        return o;
    }

    private static IndexData setInexData(String[] val) {
        String name = "当月";
        if (val[0].indexOf(name) < 0) return null;
        IndexData o = new IndexData();
        o.setCode(val[0].replace(name, "C"));
        o.setName(val[0]);
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setStartPrice(JsoupUtil.cvtDouble(val[5]));
        o.setEndPrice(JsoupUtil.cvtDouble(val[4]));
        o.setClosingPrice(JsoupUtil.cvtDouble(val[1]));
        o.setUdAmount(JsoupUtil.cvtDouble(val[2]));
        o.setUdWidth(val[3].replace("%", ""));
        o.setHighestPrice(JsoupUtil.cvtDouble(val[6]));
        o.setLowestPrice(JsoupUtil.cvtDouble(val[7]));
        o.setVolume(JsoupUtil.cvtDouble(val[8]));
        o.setTurnVolume(JsoupUtil.cvtDouble(val[9]));
        return o;
    }

    public static void setMarket() {
        String[] val;
        String[] arg = new String[]{"list.html#33", "#listview"};

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getEastList(arg);
        //if (map.size() == 0) map = HtmlUnitUtil.getEastList(arg);
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            val = entry.getValue();
            if (Constant.CODETITLE.equals(val[1])) continue;
            MarketData o = setMarketData(val);
            if (o == null) continue;
            mService.saveOrUpdate(o);
        }
    }

    private static MarketData setMarketData(String[] val) {
        MarketData o = new MarketData();
        o.setCode(val[1]);
        o.setName(val[2]);
        o.setLatestPrice(JsoupUtil.cvtDouble(val[4]));
        o.setUdWidth(JsoupUtil.cvtDouble(val[6].replace("%", "")));
        o.setUdAmount(JsoupUtil.cvtDouble(val[5]));
        o.setFiveWidth(val[14]);
        o.setVolume(JsoupUtil.cvtDouble(val[8]));
        o.setTurnVolume(JsoupUtil.cvtDouble(val[9]));
        o.setYesterdayPrice(JsoupUtil.cvtDouble(val[10]));
        o.setTodayPrice(JsoupUtil.cvtDouble(val[11]));
        o.setHighestPrice(JsoupUtil.cvtDouble(val[12]));
        o.setLowestPrice(JsoupUtil.cvtDouble(val[13]));
        o.setTurnoverRate(val[28]);
        o.setAmplitude(val[7]);
        o.setVolumeRatio(JsoupUtil.cvtDouble(val[30]));
        //o.setCommittee(JsoupUtil.cvtDouble(val[11]));
        o.setPeRatio(JsoupUtil.cvtDouble(val[29]));
        o.setNetFlag(Constant.NETFLG);
        return o;
    }

    public static void sp_rqWidth() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("industryid", "5");
        param.put("udwidth", 0);
        param.put("num", 5);
        mService.selectOne("sp_rqwidth",param);
    }

    public static void setTurnsend() {
        String[] val;
        String[] arg = new String[]{Constant.CNADDR, "javascript:View(1,1,'GongGaoRiQi',1)", "#DicList"};

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getBasicList(arg);
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            TurnsendDetail o = setTurnsendData(val);
            if (o == null) continue;
            tService.saveOrUpdate(o);
        }
    }

    private static TurnsendDetail setTurnsendData(String[] val) {
        TurnsendDetail o = new TurnsendDetail();
        o.setCode(StringUtils.stripStart(val[1], "\t  "));
        o.setName(StringUtils.stripStart(val[2],"\t  "));
        o.setPlanDate(val[4]);
        if (StringUtils.isNotEmpty(val[5])) o.setSendScale(ConvertFactory.convert(Integer.TYPE, val[5]));
        if (StringUtils.isNotEmpty(val[6])) o.setTurnScale(ConvertFactory.convert(Integer.TYPE, val[6]));
        if (StringUtils.isNotEmpty(val[7])) o.setCashScale(JsoupUtil.cvtDouble(val[7]));
        o.setPassDate(val[8]);
        o.setRegisterDate(val[9]);
        o.setDividendDate(val[10]);
        return o;
    }

    public static void setSuspension() {
        String[] val;
        String[] arg = new String[]{Constant.TFPADDR, "", "#dt_1"};

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getHrefList(arg);
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            SuspensionDetail o = setSuspensionData(val);
            if (o == null) continue;
            sService.saveOrUpdate(o);
        }
    }

    private static SuspensionDetail setSuspensionData(String[] val) {
        SuspensionDetail o = new SuspensionDetail();
        o.setCode(val[1]);
        o.setName(val[2]);
        o.setStartDate(val[4]);
        o.setEndDate(val[5]);
        o.setResumeDate(val[6]);
        o.setSuspensionPeriod(val[7]);
        o.setSuspensionReason(val[8]);
        return o;
    }
}
