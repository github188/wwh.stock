package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.net.domain.PerformanceData;
import cn.hzstk.securities.net.domain.PerformanceForecast;
import cn.hzstk.securities.net.service.PerformanceDataService;
import cn.hzstk.securities.net.service.PerformanceForecastService;
import cn.hzstk.securities.net.service.StockGroupService;
import cn.hzstk.securities.stk.domain.IndexData;
import cn.hzstk.securities.stk.domain.SelfSelect;
import cn.hzstk.securities.stk.domain.SuspendedInfo;
import cn.hzstk.securities.stk.service.HqDetailsService;
import cn.hzstk.securities.stk.service.IndexDataService;
import cn.hzstk.securities.stk.service.SelfSelectService;
import cn.hzstk.securities.stk.service.SuspendedInfoService;
import cn.hzstk.securities.util.*;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import net.ryian.core.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@SuppressWarnings("unchecked")
public class StkEastQtz {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    protected final static HqDetailsService hService = SpringContextUtil.getBean(HqDetailsService.class);
    protected final static SelfSelectService sService = SpringContextUtil.getBean(SelfSelectService.class);
    protected final static StockGroupService sgService = SpringContextUtil.getBean(StockGroupService.class);
    protected final static IndexDataService iService = SpringContextUtil.getBean(IndexDataService.class);
    protected final static SuspendedInfoService dService = SpringContextUtil.getBean(SuspendedInfoService.class);
    protected final static PerformanceDataService pService = SpringContextUtil.getBean(PerformanceDataService.class);
    protected final static PerformanceForecastService pfService = SpringContextUtil.getBean(PerformanceForecastService.class);

    public void execute() {
        try {
            Map<String, String> paramMap = new HashMap<>();
            planStock(paramMap);

            planIndex();
        } catch (Exception ex) {
            log.error("[" + this.getClass() + "]： " + ex.toString());
        }
    }

    public static void planStock(Map<String, String> paramMap) {
        String[] group = {"[class=qphox header-title mb7]","[class=box-x1 line24]"};
        String[] val;
        List<String[]> list = null;
        String siteUrl = paramMap.get("siteUrl");
        String subTitle = paramMap.get("subTitle");
        if (StringUtils.isNotEmpty(siteUrl)) {
            String[] arg = {siteUrl, "p:contains("+subTitle+")", "a:contains(全文)", ".maincont"};
            String text = HtmlUnitUtil.getDocText(arg);
            if ("早参".equals(subTitle)) {
                String tmp = text.substring(0, text.indexOf(subTitle));
                tmp += text.substring(text.indexOf("晚间"));
                text = tmp;
            }
            list = JsoupUtil.getTextList(text);
        }
        String code,dt = sgService.selectDefaultCode();
        if (list != null) {
            for (String[] aList : list) {
                code = aList[0];
                if (code.length() == 8) {
                    dt = DateUtils.format(DateUtil.formatYYYYMMDD(code).getTime());
                    continue;
                }
                val = aList;
                if (StringUtils.isEmpty(code)) val[0] = hService.getStockCode(aList[1]);
                //val = HtmlUnitUtil.getEastValue(code, group);
                setSelfData(val, dt);
            }
            return;
        }
        List<SelfSelect> listStock = sService.query(paramMap);
        for (SelfSelect aListStock : listStock) {
            val = HtmlUnitUtil.getEastValue(aListStock.getStockCode(), group);
            setSelfData(val, dt);
        }
    }

    private static void setSelfData(String[] val, String dt) {
        if (val == null || (val.length > 2 && "-".equals(val[2]))) return;
        SelfSelect o = new SelfSelect();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setDt(dt);
        if (val.length > 2) {
            o.setPrice(StkCommUtil.getSplit(val[2]));
            o.setAveragePrice(StkCommUtil.getSplit(val[3]));
            o.setChangeWidth(StkCommUtil.getSplit(val[4]).replace("%", ""));
            o.setChangeAmount(StkCommUtil.getSplit(val[5]));
            o.setTotalVolume(StkCommUtil.getSplit(val[6]));
            o.setAmount(StkCommUtil.getSplit(val[7]));
            o.setTurnOver(StkCommUtil.getSplit(val[8]));
            o.setVolumeRatio(StkCommUtil.getSplit(val[9]));
            o.setHigh(StkCommUtil.getSplit(val[11]));
            o.setLow(StkCommUtil.getSplit(val[12]));
            o.setCurOpen(StkCommUtil.getSplit(val[13]));
            o.setPreClose(StkCommUtil.getSplit(val[14]));
        }

        sService.saveOrUpdate(o);
    }

    public static void planIndex() {
        String code = "sh000001,sz399001,sz399006,sh000016,sh000300,sh000905";
        List<String[]> list = StkCommUtil.getStockList(code);
        String[] val = code.split(Constant.COMMA);
        for (int i = 0; i < list.size(); i++) {
            IndexData o = setIndexData(val[i], list.get(i));
            iService.saveOrUpdate(o);
        }
        LinkedHashMap<String, String[]> map = StkCommUtil.getStockList("index", Constant.INDEX_ADDRESS);
        if (map.size() == 0) map = StkCommUtil.getStockList("index", Constant.INDEX_ADDRESS);
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            if (DigitFormat.cvtDouble(val[9]) != 0) {
                IndexData o = setIndexData(val);
                if (o == null) continue;
                iService.saveOrUpdate(o);
            }
        }
    }

    private static IndexData setIndexData(String code,String[] val) {
        IndexData o = new IndexData();
        o.setCode(code);
        o.setName(val[0]);
        o.setDt(DateUtils.format(new Date()));
        o.setCurOpen(val[1]);

        double zs = DigitFormat.cvtDouble(val[2]);
        double dq = DigitFormat.cvtDouble(val[3]);
        o.setPreClose(val[2]);
        o.setPrice(val[3]);
        o.setChangeAmount(DigitFormat.subWidth(dq, zs));
        o.setChangeWidth(DigitFormat.calcWidth(zs, dq, zs));
        o.setHigh(val[4]);
        o.setLow(val[5]);
        o.setVolume(DigitFormat.cvtBillion(val[8]));
        o.setAmount(DigitFormat.cvtBillion(val[9]));
        return o;
    }

    private static IndexData setIndexData(String[] val) {
        String name = "当月";
        if (!val[0].contains(name)) return null;
        IndexData o = new IndexData();
        o.setCode(val[0].replace(name, "C"));
        o.setName(val[0]);
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setCurOpen(val[5]);
        o.setPreClose(val[4]);
        o.setPrice(val[1]);
        o.setChangeAmount(val[2]);
        o.setChangeWidth(val[3].replace("%", ""));
        o.setHigh(val[6]);
        o.setLow(val[7]);
        o.setVolume(val[8]);
        o.setAmount(val[9]);
        return o;
    }
/*
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
        String[] arg = new String[]{Constant.CN_ADDRESS, "javascript:View(1,1,'GongGaoRiQi',1)", "#DicList"};

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
    }*/

    public static void setSuspended() {
        String[] arg = {null,".catemk"};

        arg[0] = Constant.EAST_DATA_ADDRESS + Constant.EAST_FIRST[3] + Constant.PATH_SPLIT;
        List<String[]> list = JsoupUtil.getSiteHref(arg);

        String url = StkCommUtil.concatUrl(arg[0], list.get(2)[0]);
        String dt = dService.selectMaxStartDt();
        if (StringUtils.isEmpty(dt)) {
            arg = new String[]{url, "#dt_1"};
        } else {
            arg = new String[]{url, "#dt_1", "4", dt};
        }

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getSiteList(arg);
        String[] val;
        SuspendedInfo o;
        for (int i = 0; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            o = setSuspensionData(val);
            if (o == null) continue;
            dService.saveOrUpdate(o);
        }
    }

    private static SuspendedInfo setSuspensionData(String[] val) {
        SuspendedInfo o = new SuspendedInfo();
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setStartDate(val[4]);
        o.setEndDate(val[5]);
        o.setResumeDate(val[6]);
        o.setSuspensionPeriod(val[7]);
        o.setSuspensionReason(val[8]);
        return o;
    }

    public static void obtainPerformance() {
        String[] arg = {null,".list_summary"};

        arg[0] = Constant.EAST_DATA_ADDRESS + Constant.EAST_FIRST[1] + Constant.PATH_SPLIT;
        List<String[]> list = JsoupUtil.getSiteHref(arg);

        String url = StkCommUtil.concatUrl(Constant.EAST_DATA_ADDRESS, list.get(0)[0]);
        // StringUtils.substring(DateUtils.format(DateUtils.preWeek(new Date())), 5)
        String dt = pService.selectMaxReportDt();
        if (StringUtils.isEmpty(dt)) {
            arg = new String[]{url, "#dt_1"};
        } else {
            arg = new String[]{url, "#dt_1", "15", dt};
        }

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getSiteList(arg);
        String[] val;
        PerformanceData o;
        for (int i = 0; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            o = setPerformanceData(val, url);
            if (o == null) continue;
            pService.saveOrUpdate(o);
        }
    }

    private static PerformanceData setPerformanceData(String[] val, String url) {
        String[] str = url.split(Constant.PATH_SPLIT);
        PerformanceData o = new PerformanceData();

        o.setDt(str[4]);
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setPerProfit(val[4]);
        o.setMainRevenue(val[5]);
        o.setMainGrowth(val[6]);
        o.setMainQuarter(val[7]);
        o.setNetProfit(val[8]);
        o.setNetGrowth(val[9]);
        o.setNetQuarter(val[10]);
        o.setNetAssets(val[11]);
        o.setAssetsYield(val[12]);
        o.setOpeCashflows(val[13]);
        o.setSaleGrossprofit(val[14]);
        o.setReportDt(val[15]);
        o.setRelativeInfo(StkCommUtil.concatUrl(Constant.EAST_DATA_ADDRESS, val[18]));
        return o;
    }

    public static void obtainPerformanceForecast() {
        String[] val;
        String[] arg = {null,".list_summary"};

        arg[0] = Constant.EAST_DATA_ADDRESS + Constant.EAST_FIRST[1] + Constant.PATH_SPLIT;
        List<String[]> list = JsoupUtil.getSiteHref(arg);

        String curQuarter = DateUtil.getMonthByType(Constant.QUARTER);
        String url = list.get(2)[0].replace(curQuarter, DateUtil.getNextMonthByType(curQuarter, Constant.QUARTER));
        url = StkCommUtil.concatUrl(Constant.EAST_DATA_ADDRESS, url);
        String dt = pfService.selectMaxReportDt();
        if (StringUtils.isEmpty(dt)) {
            arg = new String[]{url, "#dt_1"};
        } else {
            arg = new String[]{url, "#dt_1", "8", dt};
        }

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getSiteList(arg);
        for (int i = 0; i < map.size(); i++) {
            val = map.get(String.valueOf(i));
            PerformanceForecast o = setPerformanceForecastData(val, url);
            if (o == null) continue;
            pfService.saveOrUpdate(o);
        }
    }

    private static PerformanceForecast setPerformanceForecastData(String[] val, String url) {
        String[] str = url.split(Constant.PATH_SPLIT);
        PerformanceForecast o = new PerformanceForecast();

        o.setDt(str[4]);
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setPerformance(val[4]);
        o.setPerformanceWidth(val[5]);
        o.setForecastType(val[6]);
        o.setNetProfit(val[7]);
        o.setReportDt(val[8]);
        o.setRelativeInfo(StkCommUtil.concatUrl(Constant.EAST_DATA_ADDRESS, val[12]));
        return o;
    }
}
