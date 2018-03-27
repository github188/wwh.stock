package cn.hzstk.securities.stockeast.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.constants.Constant;
import cn.hzstk.securities.common.constants.EastConstant;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.common.utils.DateUtil;
import cn.hzstk.securities.common.utils.DigitFormat;
import cn.hzstk.securities.common.utils.east.StkCommUtil;
import cn.hzstk.securities.east.domain.ProfitExpress;
import cn.hzstk.securities.east.domain.ProfitForecast;
import cn.hzstk.securities.east.domain.ProfitPublish;
import cn.hzstk.securities.east.domain.ProfitReport;
import cn.hzstk.securities.east.service.ProfitExpressService;
import cn.hzstk.securities.east.service.ProfitForecastService;
import cn.hzstk.securities.east.service.ProfitPublishService;
import cn.hzstk.securities.east.service.ProfitReportService;
import cn.hzstk.securities.stockeast.domain.*;
import cn.hzstk.securities.stockeast.service.*;
import cn.hzstk.securities.sys.domain.Param;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.sys.service.ParamService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/stockeast")
public class StockEastAction extends BaseMagicAction {

	@Autowired
	private CoreInfoService cService;
    @Autowired
    private StocksInfoService siService;
	@Autowired
	private SelfSelectService sService;
	@Autowired
	private StockGroupService sgService;
	@Autowired
	private ProfitReportService prService;
	@Autowired
	private ProfitExpressService peService;
	@Autowired
	private ProfitForecastService pfService;
	@Autowired
	private ProfitPublishService ppService;
	@Autowired
	private ParamService pService;
    @Autowired
    private PlateService plService;
    @Autowired
    private PlateStockService psService;

	@Autowired
	private DictService dictService;

    @RequestMapping("list")
    public String stklist(HttpServletRequest request, HttpServletResponse response) {
        //getSelfList(request, response);

        return super.getNameSpace()+"list/stklist";
    }

    @RequestMapping("detail")
    public String stkdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        if (StringUtils.isEmpty(params.get("stockCode"))) params.put("stockCode", "300059");
        params.put("dt", cService.selectMaxDt(params.get("stockCode")));

        List<CoreInfo> blist = cService.query(params);
        CoreInfo coreInfo = blist.get(0);
        String perProfit = coreInfo.getPerProfit();
        if (perProfit.length() > 6) coreInfo.setPerProfit(perProfit.substring(0,6));
        request.setAttribute("coreInfo", blist.get(0));
        request.setAttribute("m12", blist.get(0).getStockCode().startsWith("6")?"1":"2");
        request.setAttribute("m10", blist.get(0).getStockCode().startsWith("6") ? "1" : "0");

        params.put("plateType", "1");
        List<PlateStock> list = psService.query(params);
        String plateCode = list.get(0).getPlateCode();
        params.put("dt",null);
        params.put("plateCode", plateCode);
        request.setAttribute("plateCode", plateCode.substring(3));
        request.setAttribute("plateName", plService.query(params).get(0).getPlateName());

        return super.getNameSpace()+"detail/stkdetail";
    }

    @RequestMapping("zsdetail")
    public String zsdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        params.put("stype", "z");
        List<StocksInfo> silist = siService.query(params);
        request.setAttribute("stocksInfo", silist.get(0));

        return super.getNameSpace()+"detail/zsdetail";
    }

    @RequestMapping("selflist")
    public void getSelfList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = getParameterMap(request);
            String dt = params.get("dt");
            if (StringUtils.isEmpty(dt)) dt = sgService.selectDefaultCode();
            List<String> infos = sService.selectStockCode(dt);

            request.setAttribute("myStkList", infos);
            String val = StringUtils.join(infos.toArray(), Constant.COMMA);
            response.getWriter().print("var allstocklist='"+val+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("yjfp")
    public String stkyjfp(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("valid", "1");
        paramMap.put("name", "sys_start_year");
        String startym = "199012";
        List<Param> list = pService.query(paramMap);
        if (list != null || list.size() > 0) startym = list.get(0).getValue();
        String endym = DateUtil.getAnnualYear();
        List<String[]> ymList = new ArrayList<>();
        while (endym.compareTo(startym) >= 0) {
            String[] ym = new String[]{endym, DateUtil.getLastDayOfMonth(endym)};
            ymList.add(ym);
            endym = DateUtil.getNextMonthByType(endym, -1, StringUtils.EMPTY);
        }

        Map<String,String> params = getParameterMap(request);
        String dt = params.get("sel_date");
        if (StringUtils.isEmpty(dt)) dt = ymList.get(0)[0];
        String sel_date = DateUtil.getLastDayOfMonth(dt);
        String title = dt.substring(0,4)+"年";
        if ("12".equals(dt.substring(4,6))) title += "年报";
        else title += "中报";
        String id_type = params.get("id_type");
        if (StringUtils.isEmpty(id_type)) {
            id_type = "cqts_all";
        } else {
            id_type = "cqts_" + id_type;
        }

        request.setAttribute("rsdDate", sel_date);
        request.setAttribute("annualDate", dt);
        request.setAttribute("title", title);
        request.setAttribute("stat", id_type);
        request.setAttribute("ymList", ymList);
        return super.getNameSpace()+"yjfp/yjfp";
    }

    @RequestMapping("yjfpdetail")
    public String yjfpdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        String code = params.get("code");
        String name = "";
        try {
            name = URLDecoder.decode(params.get("name"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        request.setAttribute("code", code);
        request.setAttribute("name", name);
        request.setAttribute("mt", params.get("mt"));
        return super.getNameSpace()+"yjfp/yjfpdetail";
    }

    @RequestMapping("addstock")
    public String mystock(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        sService.saveOrUpdate(sgService.selectDefaultCode(), params.get("code"), "as");

        return super.getNameSpace()+"list/stklist";
    }

    @RequestMapping("yjbb")
    public String stkyjbb(HttpServletRequest request) {
        setyjbb(request, "yjbb");

        return super.getNameSpace()+"yjfp/yjbb";
    }

    @RequestMapping("yjkb")
    public String stkyjkb(HttpServletRequest request) {
        setyjbb(request, "yjkb");

        return super.getNameSpace()+"yjfp/yjkb";
    }

    private void setyjbb(HttpServletRequest request, String lb) {
        Map<String,String> params = getParameterMap(request);
        String date_type = params.get("date_type");

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("valid", "1");
        paramMap.put("name", "sys_start_yjbb");
        String startym = "200503";
        List<Param> list = pService.query(paramMap);
        if (list != null || list.size() > 0) startym = list.get(0).getValue();
        String endym = DateUtil.getMonthByType(Constant.QUARTER);
        String dt = DateUtil.getLastDayOfMonth(endym);
        if (StringUtils.isEmpty(date_type)) date_type = dt;
        request.setAttribute("date_type", date_type);

        List<String[]> ymList = new ArrayList<>();
        if (DateUtils.format(new Date()).compareTo(dt) > 0) {
            endym = DateUtil.getNextMonthByType(endym, 1, Constant.QUARTER);
            dt = DateUtil.getLastDayOfMonth(endym);
        }
        while (endym.compareTo(startym) >= 0) {
            String[] ym = new String[]{"/stockeast/" + lb + "?date_type="+dt, dt};
            ymList.add(ym);
            endym = DateUtil.getNextMonthByType(endym, -1, Constant.QUARTER);
            dt = DateUtil.getLastDayOfMonth(endym);
        }

        request.setAttribute("ymList", ymList);
    }

    @RequestMapping("yjyg")
    public String stkyjyg(HttpServletRequest request) {
        setyjbb(request, "yjyg");

        return super.getNameSpace()+"yjfp/yjyg";
    }

    @RequestMapping("yysj")
    public String stkyysj(HttpServletRequest request) {
        setyjbb(request, "yysj");

        return super.getNameSpace()+"yjfp/yysj";
    }

    @RequestMapping("bbsj")
    public String stkbbsj(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        String code = params.get("code");
        if (StringUtils.isEmpty(code)) {
            setyjbb(request, "bbsj");
            String dt = DateUtils.format(new Date());
            String initDate = pService.getbyName(ParamKeys.PROFIT_DOWN_DATE);
            String dateType = request.getAttribute("date_type").toString();
            if (!initDate.equals(dt)) {
                createProfitReport(dateType);
                pService.updatebyName(ParamKeys.PROFIT_DOWN_DATE, dt);
            } else if (DateUtil.getLastDayOfMonth(DateUtil.getNextMonthByType(dt, -2, Constant.QUARTER)).compareTo(dateType) > 0) {
                if (prService.selectCount(dateType) == 0) createProfitReport(dateType);
            } else if (StringUtils.isNotEmpty(params.get("date_type"))) {
                createProfitReport(dateType);
            }
            params = new HashMap<>();
            params.put("dt", dateType);
            PageInfo<ProfitForecast> pflist = pfService.queryPaged(params);
            request.setAttribute("pflist", pflist.getList());
            PageInfo<ProfitExpress> pelist = peService.queryPaged(params);
            request.setAttribute("pelist", pelist.getList());
            PageInfo<ProfitReport> prlist = prService.queryPaged(params);
            request.setAttribute("prlist", prlist.getList());
            PageInfo<ProfitPublish> pplist = ppService.queryPaged(params);
            request.setAttribute("pplist", pplist.getList());

            return super.getNameSpace()+"yjfp/bbsj";
        }

        params.put("stype", "a");
        List<StocksInfo> silist = siService.query(params);
        request.setAttribute("stocksInfo", silist.get(0));
        request.setAttribute("mt", silist.get(0).getPlace().equals("sh") ? "1" : "2");

        return super.getNameSpace()+"yjfp/bbsjdetail";
    }

    @RequestMapping(value = "mystock")
    public void mystock(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            JSONObject obj = new JSONObject();
            obj.put("result", "1");

			Map<String,String> params = getParameterMap(request);
            String f = params.get("f");
            if ("gs".equals(f)) {
                String g = params.get("g");
                List<String> infos = sService.selectStockCode(g);
                result.put("order", infos);
                result.put("star", "");
                result.put("Notes", "");
            } else if ("gg".equals(f)) {
                List<StockGroup> infos = sgService.query(params);
                String order = "";
                result.put("order", order);
                for (StockGroup e : infos) {
                    String groupCode = e.getGroupCode();
                    order += "," + groupCode;
                    String[] arr = new String[2];
                    arr[0] = e.getGroupName();
                    arr[1] = String.valueOf(sService.selectStockCode(groupCode).size());
                    result.put(groupCode, JSON.parseArray(JSON.toJSONString(arr, true)));
                }
                result.put("order", order.substring(1));
            } else if ("ag".equals(f)) {
                String gn = new String(params.get("gn").getBytes("ISO8859-1"), "UTF-8");
				String groupCode = sgService.saveOrUpdate(null, gn);
				if ("-1".equals(groupCode)) {
					obj.put("result", "-1");
					result.put("msg", "组合名称重复");
				} else {
					result.put("msg", "添加组合成功");
				}
            } else if ("dg".equals(f)) {
                String g = params.get("g");
				String groupCode = sgService.saveOrUpdate(g, null);
				if ("-1".equals(groupCode)) {
					obj.put("result", "-1");
					result.put("msg", "该组合不存在");
				} else {
					result.put("msg", "删除组合成功");
				}
            } else if ("mg".equals(f)) {
                String g = params.get("g");
                String gn = new String(params.get("gn").getBytes("ISO8859-1"), "UTF-8");
                //        URLDecoder.decode(params.get("gn"), "utf-8");
				String groupCode = sgService.saveOrUpdate(g, gn);
				if ("-1".equals(groupCode)) {
					obj.put("result", "-1");
					result.put("msg", "组合名称重复");
				} else {
					result.put("gid", groupCode);
					result.put("msg", "修改组合成功");
				}
            } else if ("as".equals(f)) {
                String g = params.get("g");
                String sc = params.get("sc");
				if (sService.saveOrUpdate(g, sc, f) == -1) {
					obj.put("result", "-1");
					result.put("msg", "组合中已存在该股票");
				} else {
					result.put("msg", "添加股票成功");
				}
            } else if ("ds".equals(f) || "dslot".equals(f)) {
                String g = params.get("g");
                String sc = params.get("sc");
				for (String code : sc.split(",")) {
					sService.saveOrUpdate(g, code, f);
				}
				result.put("msg", "删除股票成功");
            }

            obj.put("data", result);
            response.getWriter().print(params.get("cb")+"("+obj.toJSONString()+");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "selfsave")
    public void selfsave(HttpServletRequest request, HttpServletResponse response){
        try {
            String codeList = request.getParameter("codeList");
            String groupCode = "";
            if (StringUtils.isEmpty(codeList)) {
                codeList = request.getParameter("yjfpList");
                if (StringUtils.isEmpty(codeList)) return;
                groupCode = sgService.selectYjfp();
            }

            if (StringUtils.isEmpty(groupCode)) groupCode = sgService.selectDefaultCode();
            for (String code : codeList.split(",")) {
                SelfSelect o = new SelfSelect();
                o.setDt(groupCode);
                o.setStockCode(code);
                sService.saveOrUpdate(o);
            }
            JSONObject obj = new JSONObject();
            obj.put("result", "1");
            response.getWriter().print("("+obj.toJSONString()+");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "profitsave")
    public String profitsave(HttpServletRequest request, HttpServletResponse response){
        String codeList = request.getParameter("code_list");

        for (String pr : codeList.split(",")) {
            if (StringUtils.isEmpty(pr)) break;
            ProfitReport o = new ProfitReport();
            String[] str = pr.split("\\|");
            if (!str[0].startsWith("0") && !str[0].startsWith("6") && !str[0].startsWith("3")) continue;
            o.setDt(str[17]);
            o.setStockCode(str[0]);
            o.setStockName(str[1]);
            o.setPerProfit(str[2]);
            o.setBusinessProfit(DigitFormat.formatNumber(str[4],-4));
            o.setYearGrowth(DigitFormat.formatNumber(str[5]));
            o.setChainGrowth(DigitFormat.formatNumber(str[6]));
            o.setNetProfit(DigitFormat.formatNumber(str[7]));
            o.setNetYearGrowth(DigitFormat.formatNumber(str[8]));
            o.setNetChainGrowth(DigitFormat.formatNumber(str[9]));
            o.setNetAssets(DigitFormat.formatNumber(str[10],4));
            o.setAssetsYield(DigitFormat.formatNumber(str[11]));
            o.setCashFlow(DigitFormat.formatNumber(str[12],4));
            o.setGrossProfit(DigitFormat.formatNumber(str[13]));
            o.setReportDate(str[16]);
            prService.saveOrUpdate(o);
        }
        return stkyjbb(request);
    }

    @RequestMapping("stkcalendar")
    public String stkcalendar(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        /*List<BasicInfo> blist = bService.query(params);
        request.setAttribute("basicInfo", blist.get(0));*/

        return super.getNameSpace()+"feature/calendar";
    }

	private static Map<String, String> orderByMap = new HashMap<String, String>();
	static {
		orderByMap.put("1", "create_date");
		orderByMap.put("2", "end_time");
		orderByMap.put("3", "work_num");
		orderByMap.put("4", "task_cash");
	}
	
	private static Map<String, String> ascMap = new HashMap<String, String>();
	static {
		ascMap.put("0", "desc");
		ascMap.put("1", "asc");
	}

    private void createProfitReport(String dt) {
        try {
            String str;
            List<String[]> list;
            /*String endym = DateUtil.getMonthByType(Constant.QUARTER);
            String dt = DateUtil.getLastDayOfMonth(endym);*/
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    str = EastConstant.YJYG_PARAM[0];
                    str = str.replace(Constant.REPLACE_STR1, dt);
                    list = StkCommUtil.getexJsonList(str.replace(Constant.REPLACE_STR, EastConstant.YJBB_LIST[i]));
                } else {
                    if (i == 3) {
                        str = EastConstant.YYSJ_PARAM[0];
                    } else {
                        str = EastConstant.YJBB_PARAM[0];
                    }
                    str = str.replace(Constant.REPLACE_STR1, dt);
                    list = StkCommUtil.getJsonList(str.replace(Constant.REPLACE_STR, EastConstant.YJBB_LIST[i]));
                }
                for (String[] aList : list) {
                    if (!aList[0].startsWith("0") && !aList[0].startsWith("6") && !aList[0].startsWith("3")) continue;
                    if (i == 0) {
                        ProfitForecast o = setProfitForecast(aList);
                        pfService.saveOrUpdate(o);
                    } else if (i == 1) {
                        ProfitExpress o = setProfitExpress(aList);
                        peService.saveOrUpdate(o);
                    } else if (i == 2) {
                        ProfitReport o = setProfitReport(aList);
                        prService.saveOrUpdate(o);
                    } else if (i == 3) {
                        ProfitPublish o = setProfitPublish(aList);
                        ppService.saveOrUpdate(o);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("[createProfitReport]： "+ex.toString());
        }
    }

    private ProfitForecast setProfitForecast(String[] val) {
        ProfitForecast o = new ProfitForecast();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setDt(val[8]);
        o.setProfitChange(val[2]);
        o.setProfitChangeWidth(val[3]);
        o.setForecastType(val[4]);
        o.setNetLastPeriod(DigitFormat.formatNumber(val[5], -4));
        o.setReportDate(val[7]);

        return o;
    }

    private ProfitExpress setProfitExpress(String[] val) {
        ProfitExpress o = new ProfitExpress();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setPerProfit(val[2]);
        o.setBusinessProfit(DigitFormat.formatNumber(val[3], -4));
        o.setBusinessLastPeriod(DigitFormat.formatNumber(val[4], -4));
        o.setYearGrowth(DigitFormat.formatNumber(val[5]));
        o.setChainGrowth(DigitFormat.formatNumber(val[6]));
        o.setNetProfit(DigitFormat.formatNumber(val[7], -4));
        o.setNetLastPeriod(DigitFormat.formatNumber(val[8], -4));
        o.setDt(val[14]);
        o.setNetYearGrowth(DigitFormat.formatNumber(val[9]));
        o.setNetChainGrowth(DigitFormat.formatNumber(val[10]));
        o.setNetAssets(DigitFormat.formatNumber(val[11], 2));
        o.setAssetsYield(DigitFormat.formatNumber(val[12]));
        o.setReportDate(val[13]);

        return o;
    }

    private ProfitReport setProfitReport(String[] val) {
        ProfitReport o = new ProfitReport();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setPerProfit(val[2]);
        o.setBusinessProfit(DigitFormat.formatNumber(val[4], -4));
        o.setYearGrowth(DigitFormat.formatNumber(val[5]));
        o.setChainGrowth(DigitFormat.formatNumber(val[6]));
        o.setNetProfit(DigitFormat.formatNumber(val[7], -4));
        o.setDt(val[17]);
        o.setNetYearGrowth(DigitFormat.formatNumber(val[8]));
        o.setNetChainGrowth(DigitFormat.formatNumber(val[9]));
        o.setNetAssets(DigitFormat.formatNumber(val[10], 4));
        o.setAssetsYield(DigitFormat.formatNumber(val[11]));
        o.setCashFlow(DigitFormat.formatNumber(val[12], 4));
        o.setGrossProfit(DigitFormat.formatNumber(val[13]));
        o.setMemo(val[14]);
        o.setReportDate(val[16]);

        return o;
    }

    private ProfitPublish setProfitPublish(String[] val) {
        ProfitPublish o = new ProfitPublish();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setOrderDate(val[2]);
        if (StringUtils.isNotEmpty(val[3])) o.setOnceChangeDate(val[3]);
        if (StringUtils.isNotEmpty(val[4])) o.setSecondChangeDate(val[4]);
        if (StringUtils.isNotEmpty(val[5])) o.setThirdChangeDate(val[5]);
        o.setReportDate(val[6]);
        o.setDt(val[7]);

        return o;
    }

}
