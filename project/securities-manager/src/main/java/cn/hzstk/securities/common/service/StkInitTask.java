package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.constants.EastConstant;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.net.domain.*;
import cn.hzstk.securities.net.domain.FundDetails;
import cn.hzstk.securities.net.domain.Plate;
import cn.hzstk.securities.net.domain.PlateStock;
import cn.hzstk.securities.net.service.*;
import cn.hzstk.securities.net.service.FundDetailsService;
import cn.hzstk.securities.net.service.PlateService;
import cn.hzstk.securities.net.service.PlateStockService;
import cn.hzstk.securities.stk.domain.*;
import cn.hzstk.securities.stk.service.*;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.tot.domain.StockWidth;
import cn.hzstk.securities.tot.service.StockWidthService;
import cn.hzstk.securities.util.DateUtil;
import cn.hzstk.securities.util.DigitFormat;
import cn.hzstk.securities.util.GB2Alpha;
import cn.hzstk.securities.util.StkCommUtil;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import net.ryian.core.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class StkInitTask {
    protected static Logger log = Logger.getLogger(Constant.LOG_FILE);
    protected static Logger logger = Logger.getLogger(Constant.LOG_ERROR);

    private final static HqDetailsService hService = SpringContextUtil.getBean(HqDetailsService.class);
    private final static IndexDataService iService = SpringContextUtil.getBean(IndexDataService.class);
    private final static RxDataService rService = SpringContextUtil.getBean(RxDataService.class);
    private final static StockWidthService swService = SpringContextUtil.getBean(StockWidthService.class);
    private final static PlateService pService = SpringContextUtil.getBean(PlateService.class);
    private final static PlateStockService psService = SpringContextUtil.getBean(PlateStockService.class);
    private final static StocksInfoService siService = SpringContextUtil.getBean(StocksInfoService.class);
    private final static FundDetailsService fService = SpringContextUtil.getBean(FundDetailsService.class);
    private final static ProfitReportService prService = SpringContextUtil.getBean(ProfitReportService.class);
    private final static TigerDataService tService = SpringContextUtil.getBean(TigerDataService.class);
    private final static InvestCalendarService icService = SpringContextUtil.getBean(InvestCalendarService.class);
    private final static ParamService paramService = SpringContextUtil.getBean(ParamService.class);
    private final static CoreInfoService cService = SpringContextUtil.getBean(CoreInfoService.class);
    private final static SelfSelectService sService = SpringContextUtil.getBean(SelfSelectService.class);
    private final static StockGroupService sgService = SpringContextUtil.getBean(StockGroupService.class);

    public static void initData(String dt) {
        if (createQuotation(dt) == 0) return;
        createExponent(dt);
        createFundDetails(dt);
        createPlate(dt);
        createInvestCalendar();
    }

    public static void initHoliData(String dt) {
        //createInfo();
        createProfitReport();
        createPlateStock(dt);
        updateYJFP();
        updateCoreInfo();
        updateTigerInfo();
        createInvestCalendar();
    }

    private static int createQuotation(String dt) {
        try {
            log.info("行情数据生成开始时间："+DateUtils.formatTime(new Date()));
            if (hService.queryCntByDt(dt) > Constant.DATA_COUNT) return 0;

            List<String[]> list = StkCommUtil.getJsonList(EastConstant.HQ_PARAM[0].replace(Constant.REPLACE_STR, EastConstant.HQ_LIST[0]));
            for (String[] aList : list) {
                StocksInfo oSi = setInfo(aList);
                siService.saveOrUpdate(oSi);
                if ("0.00".equals(aList[9]) || "-".equals(aList[8])) continue;
                HqDetails o = setQuotation(aList, dt);
                hService.saveOrUpdate(o);
                RxData oRx = setRxData(aList, dt);
                rService.saveOrUpdate(oRx);
                StockWidth oSw = setStockWidth(aList, dt);
                swService.saveOrUpdate(oSw);
            }

            log.info("行情数据生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[execute]： "+ex.toString());
        }
        return 1;
    }

    private static HqDetails setQuotation(String[] val, String dt) {
        HqDetails o = new HqDetails();
        o.setDt(dt);
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setPrice(val[3]);
        o.setChangeAmount(val[4]);
        o.setChangeWidth(val[5].replace("%", ""));
        //o.setBuyPrice(val[5]);
        //o.setSalePrice(val[6]);
        //o.setVolume(val[8]);
        //o.setAmplitude(val[6]);
        o.setTotalVolume(val[7]);
        o.setAmount(val[8]);
        o.setPreClose(val[9]);
        o.setCurOpen(val[10]);
        o.setHigh(val[11]);
        o.setLow(val[12]);
        o.setChangeRate(val[21].replace("%", ""));
        o.setVolumeRatio(val[22]);
        o.setTurnOver(val[23]);
        o.setPe(val[24]);
        return o;
    }

    private static RxData setRxData(String[] val, String dt) {
        RxData o = new RxData();
        o.setDt(dt);
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setCurOpen(val[10]);
        o.setHigh(val[11]);
        o.setLow(val[12]);
        o.setCurClose(val[3]);
        o.setVolume(val[7]);
        /*o.setMa1(val[6]);
        o.setMa2(val[7]);
        o.setMa3(val[8]);
        o.setMa4(val[9]);
        o.setMavol1(val[13]);
        o.setMavol2(val[14]);
        o.setK(val[15]);
        o.setD(val[16]);
        o.setJ(val[17]);*/
        return o;
    }

    private static StockWidth setStockWidth(String[] val, String dt) {
        StockWidth o = new StockWidth();
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setStartDt(DateUtils.format(new Date()));
        o.setCurPrice(val[3]);
        o.setUpWidth(val[5].replace("%", ""));
        return o;
    }

    private static void createExponent(String dt) {
        try {
            log.info("指数数据生成开始时间：" + DateUtils.formatTime(new Date()));
            //String code = "0000011,3990012,3990062,0000161,0003001,0009051";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("stype", "z");
            List<StocksInfo> tmpList = siService.query(paramMap);
            StringBuilder code = new StringBuilder(StringUtils.EMPTY);
            String tmp;
            for (StocksInfo o : tmpList) {
                if ("sh".equals(o.getPlace())) {
                    tmp = o.getCode() + "1";
                } else {
                    tmp = o.getCode() + "2";
                }
                code.append(Constant.COMMA).append(tmp);
            }
            code = new StringBuilder(code.substring(1));
            //code = "0000011,3990012,0003001,3990052,3990062,0000031,0000021,3991062,3990022,3990032,0000161,3995502";
            String param = EastConstant.hqdigi2 + EastConstant.zy.replace(Constant.REPLACE_STR, code.toString());
            List<String[]> list = StkCommUtil.getJsonList(param);
            for (String[] aList : list) {
                IndexData o = setExponent(dt, aList);
                iService.saveOrUpdate(o);
            }

            list = StkCommUtil.getJsonList(EastConstant.INDEX_COUNT_PARAM);
            code = new StringBuilder("sh000001,sz399001,sz399006");
            String[] val;
            for (int i = 0; i < list.get(0).length; i++) {
                val = list.get(0)[i].split(Constant.DELIMITER);
                IndexData o = addExponent(dt, code.toString().split(Constant.COMMA)[i], val);
                iService.saveOrUpdate(o);
            }
            log.info("指数数据生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[execute]： "+ex.toString());
        }
    }

    private static IndexData setExponent(String dt, String[] val) {
        IndexData o = new IndexData();
        if (val[0].endsWith("1")) {
            o.setCode("sh" + val[1]);
        } else {
            o.setCode("sz" + val[1]);
        }
        o.setName(val[2]);
        o.setDt(dt);
        o.setCurOpen(val[4]);
        o.setPreClose(val[3]);
        o.setPrice(val[5]);
        o.setChangeAmount(val[10]);
        o.setChangeWidth(val[11]);
        o.setHigh(val[6]);
        o.setLow(val[7]);
        o.setVolume(DigitFormat.cvtBillion(val[9]));
        o.setAmount(DigitFormat.cvtMillion(val[8]));
        //o.setChangeRate(val[50]);
        return o;
    }

    private static IndexData addExponent(String dt, String code,String[] val) {
        IndexData o = new IndexData();
        o.setCode(code);
        o.setDt(dt);
        o.setRiseCnt(val[0]);
        o.setFlatCnt(val[1]);
        o.setFallCnt(val[2]);
        //o.setStopCnt(val[3]);
        return o;
    }

    public static void createInfo() {
        try {
            log.info("股票信息生成开始时间："+DateUtils.formatTime(new Date()));
            List<String[]> list = StkCommUtil.getJsonList(EastConstant.HQ_PARAM[0].replace(Constant.REPLACE_STR, EastConstant.HQ_LIST[0]));
            for (String[] aList : list) {
                StocksInfo o = setInfo(aList);
                siService.saveOrUpdate(o);
            }

            String code = "0000011,3990012,0003001,3990052,3990062,0000031,0000021,3991062,3990022,3990032,0000161,3995502";
            String param = EastConstant.hqdigi2 + EastConstant.zy.replace(Constant.REPLACE_STR, code);
            list = StkCommUtil.getJsonList(param);
            for (String[] aList : list) {
                StocksInfo o = setInfo(aList,"z");
                siService.saveOrUpdate(o);
            }
            log.info("股票信息生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[createInfo]： "+ex.toString());
        }
    }

    private static StocksInfo setInfo(String[] val) {
        return setInfo(val, "a");
    }

    private static StocksInfo setInfo(String[] val, String sType) {
        StocksInfo o = new StocksInfo();
        o.setCode(val[1]);
        o.setName(val[2]);
        GB2Alpha obj = new GB2Alpha();
        String plateName = obj.String2Alpha(val[2]);
        o.setAbbreviation(plateName);
        o.setPlace("1".equals(StringUtils.right(val[0], 1)) ? "sh" : "sz");
        o.setStype(sType);
        if ("z".equals(sType)) {
            o.setStatus(0);
        } else {
            o.setStatus("0.00".equals(val[9]) ? 2 : ("-".equals(val[8]) ? 1 : 0));
        }
        return o;
    }

    private static void createPlate(String dt) {
        try {
            log.info("板块信息生成开始时间："+DateUtils.formatTime(new Date()));
            List<String[]> list;
            for (int i = 0; i < 3; i++){
                list = StkCommUtil.getJsonList(EastConstant.BK_PARAM[0].replace(Constant.REPLACE_STR, EastConstant.BK_LIST[i]));
                for (String[] aList : list) {
                    Plate o = setPlate(aList, String.valueOf(i+1), dt);
                    pService.saveOrUpdate(o);
                }
            }
            log.info("板块信息生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[createPlate]： "+ex.toString());
        }
    }

    private static Plate setPlate(String[] val, String plateType, String dt) {
        Plate o = new Plate();
        o.setDt(dt);
        o.setPlateType(plateType);
        o.setPlateCode(val[1]);
        o.setPlateName(val[2]);
        o.setChangeWidth(val[3]);
        o.setAmount(DigitFormat.cvtBillion(val[4]));
        o.setTurnOver(val[5]);
        o.setUpNum(val[6].split(Constant.DELIMITER)[0]);
        o.setDownNum(val[6].split(Constant.DELIMITER)[2]);
        o.setStockCode(val[7]);
        o.setStockName(val[9]);
        o.setStockChangeWidth(val[11]);
        return o;
    }

    private static void createPlateStock(String dt) {
        try {
            log.info("板块关联信息生成开始时间："+DateUtils.formatTime(new Date()));
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("dt", dt);
            List<Plate> pList = pService.query(paramMap);
            String plate_code,cmd;
            List<String[]> list;
            boolean cockFlg;
            for (Plate plate : pList) {
                plate_code = plate.getPlateCode();
                cmd = "BK0" + plate_code.substring(plate_code.length() - 3, plate_code.length()) + "1";
                list = StkCommUtil.getJsonList(EastConstant.LIST_PARAM[0].replace(Constant.REPLACE_STR, cmd));
                cockFlg = true;
                for (String[] aList : list) {
                    PlateStock o = setPlateStock(aList, plate.getPlateType(), plate_code, cockFlg);
                    if (cockFlg) cockFlg = false;
                    psService.saveOrUpdate(o);
                }
            }
            log.info("板块关联信息生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[createPlateStock]： "+ex.toString());
        }
    }

    private static PlateStock setPlateStock(String[] val, String plate_type, String plate_code, boolean cockFlg) {
        PlateStock o = new PlateStock();
        o.setPlateType(plate_type);
        o.setPlateCode(plate_code);
        o.setStockCode(val[1]);
        o.setCockNum(0);
        if (cockFlg) {
            o.setCockNum(1);
        } else if (DigitFormat.getLimit(val[9]).equals(val[3])) {
            o.setCockNum(1);
        }
        return o;
    }

    private static void createFundDetails(String dt) {
        try {
            log.info("资金流向数据生成开始时间："+DateUtils.formatTime(new Date()));
            List<String[]> list = StkCommUtil.getJsonList(EastConstant.ZJLX_PARAM[0]);
            for (String[] aList : list) {
                FundDetails o = setFundDetails(aList, dt);
                fService.saveOrUpdate(o);
            }
            log.info("资金流向数据生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[createFundDetails]： "+ex.toString());
        }
    }

    private static FundDetails setFundDetails(String[] val, String dt) {
        FundDetails o = new FundDetails();
        o.setDt(dt);
        o.setStockCode(val[1]);
        o.setStockName(val[2]);
        o.setChangeWidth(val[4]);
        o.setPrice(val[3]);
        o.setMainAmount(val[5]);
        o.setMainDuty(val[6]);
        o.setLargeAmount(val[7]);
        o.setLargeDuty(val[8]);
        o.setBigAmount(val[9]);
        o.setBigDuty(val[10]);
        o.setMiddleAmount(val[11]);
        o.setMiddleDuty(val[12]);
        o.setSmallAmount(val[13]);
        o.setSmallDuty(val[14]);
        return o;
    }

    private static void createInvestCalendar() {
        try {
            log.info("投资日历生成开始时间："+DateUtils.formatTime(new Date()));
            List<Map<String, Object>> list = StkCommUtil.getJsonListMap(EastConstant.INVEST_PARAM, DateUtils.format(new Date()));
            for (Map<String, Object> aList : list) {
                InvestCalendar o = setInvestCalendar(aList);
                icService.saveOrUpdate(o);
            }
            log.info("投资日历生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[createInvestCalendar]： "+ex.toString());
        }
    }

    private static InvestCalendar setInvestCalendar(Map<String, Object> map) {
        InvestCalendar o = new InvestCalendar();
        o.setDt((String) map.get("STARTDATE"));
        o.setStockCode(((String) map.get("SECUCODE")).substring(0, 6));
        o.setStockName((String) map.get("SECURITYSHORTNAME"));
        o.setInvestType("股东大会召开日");
        o.setMemo((String) map.get("CONTENT"));
        return o;
    }

    private static void createProfitReport() {
        try {
            List<String[]> list;
            String endym = DateUtil.getMonthByType(Constant.QUARTER);
            String dt = DateUtil.getLastDayOfMonth(endym);
            String str = EastConstant.YJBB_PARAM[0];
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    str = EastConstant.YJYG_PARAM[0];
                    str = str.replace(Constant.REPLACE_STR1, dt);
                    list = StkCommUtil.getexJsonList(str.replace(Constant.REPLACE_STR, EastConstant.YJBB_LIST[i]));
                } else {
                    if (i == 3) str = EastConstant.YYSJ_PARAM[0];
                    str = str.replace(Constant.REPLACE_STR1, dt);
                    list = StkCommUtil.getJsonList(str.replace(Constant.REPLACE_STR, EastConstant.YJBB_LIST[i]));
                }
                for (String[] aList : list) {
                    if (!aList[0].startsWith("0") && !aList[0].startsWith("6") && !aList[0].startsWith("3")) continue;
                    ProfitReport o = setProfitReport(aList, i);
                    prService.saveOrUpdate(o);
                }
            }
        } catch (Exception ex) {
            logger.error("[createProfitReport]： "+ex.toString());
        }
    }

    private static ProfitReport setProfitReport(String[] val, int i) {
        ProfitReport o = new ProfitReport();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        if (i == 3) {
            o.setOrderDate(val[2]);
            if (StringUtils.isNotEmpty(val[3])) o.setOrderDate(val[3]);
            if (StringUtils.isNotEmpty(val[4])) o.setOrderDate(val[4]);
            if (StringUtils.isNotEmpty(val[5])) o.setOrderDate(val[5]);
            o.setDt(val[7]);
        } else if (i > 0) {
            o.setPerProfit(val[2]);
            if (i == 2) {
                o.setBusinessProfit(DigitFormat.formatNumber(val[4], -4));
            } else if (i == 1) {
                o.setBusinessProfit(DigitFormat.formatNumber(val[3], -4));
            }
            o.setYearGrowth(DigitFormat.formatNumber(val[5]));
            o.setChainGrowth(DigitFormat.formatNumber(val[6]));
            o.setNetProfit(DigitFormat.formatNumber(val[7], 4));
            if (i == 2) {
                o.setDt(val[17]);
                o.setNetYearGrowth(DigitFormat.formatNumber(val[8]));
                o.setNetChainGrowth(DigitFormat.formatNumber(val[9]));
                o.setNetAssets(DigitFormat.formatNumber(val[10], 4));
                o.setAssetsYield(DigitFormat.formatNumber(val[11]));
                o.setCashFlow(DigitFormat.formatNumber(val[12], 4));
                o.setGrossProfit(DigitFormat.formatNumber(val[13]));
                o.setMemo(val[14]);
                o.setReportDate(val[16]);
            } else if (i == 1) {
                o.setDt(val[14]);
                o.setNetYearGrowth(DigitFormat.formatNumber(val[9]));
                o.setNetChainGrowth(DigitFormat.formatNumber(val[10]));
                o.setNetAssets(DigitFormat.formatNumber(val[11], 4));
                o.setAssetsYield(DigitFormat.formatNumber(val[12]));
                o.setReportDate(val[13]);
            }
        } else {
            o.setDt(val[8]);
            o.setNetProfit(DigitFormat.formatNumber(val[5], -4));
            o.setNetYearGrowth(val[3]);
            o.setMemo(val[2]);
            o.setReportDate(val[7]);
        }
        return o;
    }

    private static void updateYJFP() {
        try {
            log.info("业绩分配数据生成开始时间："+DateUtils.formatTime(new Date()));
            String endym = DateUtil.getMonthByType(Constant.QUARTER);
            String dt = DateUtil.getLastDayOfMonth(endym);
            String groupCode = sgService.selectYjfp();
            if (StringUtils.isEmpty(groupCode)) groupCode = sgService.selectDefaultCode();
            List<Map<String, Object>> list = StkCommUtil.getJsonListMap(EastConstant.YJFP_PARAM, dt);
            for (Map<String, Object> aList : list) {
                SelfSelect o = new SelfSelect();
                if ("0.0".equals(aList.get("SZZBL"))) continue;
                o.setDt(groupCode);
                o.setStockCode((String)aList.get("Code"));
                sService.saveOrUpdate(o);
            }
            log.info("业绩分配数据生成结束时间："+DateUtils.formatTime(new Date()));
        } catch (Exception ex) {
            logger.error("[updateYJFP]： "+ex.toString());
        }
    }

    private static void updateTigerInfo() {
        try {
            String sCode = paramService.getbyName(ParamKeys.TIGER_DEFAULT_CODE);
            if (StringUtils.isEmpty(sCode)) sCode = "0";
            List<StocksInfo> sList = siService.query(new HashMap<>());
            Map<String, Object> map = null;
            for (StocksInfo aList : sList) {
                if (aList.getCode().compareTo(sCode) < 0) continue;
                if (aList.getStatus() == 2 || "z".equals(aList.getStype())) continue;
                do {
                    map = StkCommUtil.getJsonMap(EastConstant.TIGER_PARAM[0].replace(Constant.REPLACE_STR, aList.getCode()), EastConstant.TIGER_PARAM[1]);
                } while (map != null && map.get("SCode") == null);
                if (map == null) {
                    paramService.updatebyName(ParamKeys.TIGER_DEFAULT_CODE, aList.getCode());
                    break;
                }
                TigerData oTd = setTigerData(map);
                tService.saveOrUpdate(oTd);
            }
            if (map != null) {
                paramService.updatebyName(ParamKeys.TIGER_DEFAULT_CODE, null);
            }
        } catch (Exception ex) {
            logger.error("[updateTigerInfo]： "+ex.toString());
        }
    }

    private static TigerData setTigerData(Map<String, Object> map) {
        TigerData o = new TigerData();
        o.setStockCode((String) map.get("SCode"));
        o.setStockName((String) map.get("SName"));
        o.setPrice((String) map.get("Price"));
        o.setChangeRate((String) map.get("ChgRate"));
        o.setTurnRate((String) map.get("TurnRate"));
        o.setCurrentValue(DigitFormat.cvtBillion((String) map.get("AGSZBHXS")));
        o.setUpCount1m((String) map.get("UpCount1M"));
        o.setUpCount3m((String) map.get("UpCount3M"));
        o.setUpCount6m((String) map.get("UpCount6M"));
        o.setChangeWidth1m((String) map.get("RChange1M"));
        o.setChangeWidth3m((String) map.get("RChange3M"));
        o.setChangeWidth6m((String) map.get("RChange6M"));
        o.setChangeWidth1y((String) map.get("RChange1Y"));
        return o;
    }

    private static void updateCoreInfo() {
        try {
            List<StocksInfo> sList = siService.query(new HashMap<>());
            List<String[]> list;
            for (StocksInfo aList : sList) {
                if (aList.getStatus() == 2 || "z".equals(aList.getStype())) continue;
                list = StkCommUtil.getJsonList(
                        EastConstant.HXSJ_PARAM[0].replace(Constant.REPLACE_STR, aList.getCode()).replace(Constant.REPLACE_STR1, "sh".equals(aList.getPlace()) ? "1" : "2"));
                CoreInfo o = setCoreInfo(list.get(0), aList.getName());
                cService.saveOrUpdate(o);
            }
        } catch (Exception ex) {
            logger.error("[updateCoreInfo]： "+ex.toString());
        }
    }

    private static CoreInfo setCoreInfo(String[] val,String name) {
        CoreInfo o = new CoreInfo();
        o.setDt(val[3]);
        o.setStockCode(val[1]);
        o.setStockName(name);
        o.setPerProfit(val[2]);
        o.setPe(val[4]);
        o.setNetAssets(val[5]);
        o.setPbRatio(val[6]);
        o.setMainRevenue(DigitFormat.cvtBillion(val[7]));
        o.setMainYoy(val[8]);
        o.setNetProfit(DigitFormat.cvtBillion(val[9]));
        o.setNetYoy(val[10]);
        o.setGrossProfitYield(val[11]);
        o.setNetProfitRatio(val[12]);
        o.setAssetsYield(val[13]);
        o.setDebitRatio(val[14]);
        o.setTotalEquity(DigitFormat.cvtBillion(val[15]));
        o.setTotalAmount(DigitFormat.cvtBillion(val[16]));
        o.setCirculationEquity(DigitFormat.cvtBillion(val[17]));
        o.setFlowAmount(DigitFormat.cvtBillion(val[18]));
        o.setNdistribProfit(val[19]);
        o.setOpenDate(val[20]);
        return o;
    }
}
