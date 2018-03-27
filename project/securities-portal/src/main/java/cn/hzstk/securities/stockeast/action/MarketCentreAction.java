package cn.hzstk.securities.stockeast.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.east.domain.SelectDetail;
import cn.hzstk.securities.east.domain.SelectFirst;
import cn.hzstk.securities.east.domain.SelectList;
import cn.hzstk.securities.east.service.SelectDetailService;
import cn.hzstk.securities.east.service.SelectFirstService;
import cn.hzstk.securities.east.service.SelectListService;
import cn.hzstk.securities.stockeast.domain.Plate;
import cn.hzstk.securities.stockeast.domain.StocksInfo;
import cn.hzstk.securities.stockeast.service.PlateService;
import cn.hzstk.securities.stockeast.service.StocksInfoService;
import cn.hzstk.securities.sys.service.DictService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stockeast")
public class MarketCentreAction extends BaseMagicAction {

    @Autowired
    private StocksInfoService siService;
	@Autowired
    private PlateService plService;
	@Autowired
	private SelectFirstService sfService;
	@Autowired
    private SelectListService slService;
	@Autowired
	private SelectDetailService sdService;

	@Autowired
	private DictService dictService;

    @RequestMapping("marketcentre")
    public String centre(HttpServletRequest request) {
        //getSelfList(request);

        return super.getNameSpace()+"market/marketcentre";
    }

    @RequestMapping("marketlist")
    public String list(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        //getSelfList(request);

        return super.getNameSpace()+"market/marketlist";
    }

    @RequestMapping("center")
    public String center(HttpServletRequest request) {

        return super.getNameSpace()+"market/datacenter";
    }

    @RequestMapping("bankuai")
    public String bankuai(HttpServletRequest request) {

        return super.getNameSpace()+"market/bankuai";
    }

    @RequestMapping("bklist")
    public String bklist(HttpServletRequest request) {

        return super.getNameSpace()+"market/bklist";
    }

    @RequestMapping("xglist")
    public String xglist(HttpServletRequest request) {

        return super.getNameSpace()+"xg/xglist";
    }

    @RequestMapping("zjlx/list")
    public String zjlxlist(HttpServletRequest request) {

        return super.getNameSpace()+"zj/zjlxlist";
    }

    @RequestMapping("dpzjlx")
    public String dpzjlx(HttpServletRequest request) {

        return super.getNameSpace()+"zj/dpzjlx";
    }

    @RequestMapping("bkzjlx")
    public String bkzjlx(HttpServletRequest request) {

        return super.getNameSpace()+"zj/bkzjlx";
    }

    @RequestMapping("bkzj/list")
    public String bkzjlist(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        String code = params.get("code");
        String name = "行业";
        String width = "1500px";
        if ("GN".equals(code)) {
            name = "概念";
            width = "5000px";
        } else if ("DY".equals(code)) {
            name = "地域";
            width = "1080px";
        }
        request.setAttribute("code", code);
        request.setAttribute("name", name);
        request.setAttribute("sty", "DCFFITABK");
        request.setAttribute("width", width);

        return super.getNameSpace()+"zj/bkzjlist";
    }

    @RequestMapping("bkzj/detail")
    public String bkzjdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);

        String name = "";
        try {
            name = URLDecoder.decode(params.get("name"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String plateType = "1";
        if ("概念".equals(name)) {
            plateType = "2";
        } else if ("地域".equals(name)) {
            plateType = "3";
        }
        params.put("plateType", plateType);
        Plate plate = plService.query(params).get(0);

        request.setAttribute("name", name);
        request.setAttribute("plateInfo", plate);

        return super.getNameSpace()+"zj/bkzjdetail";
    }

    @RequestMapping("zjlx/detail")
    public String zjlxdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        params.put("stype", "a");
        List<StocksInfo> silist = siService.query(params);
        request.setAttribute("stocksInfo", silist.get(0));

        return super.getNameSpace()+"zj/zjlxdetail";
    }

    @RequestMapping("zjlxcenter")
    public String zjlxcentre(HttpServletRequest request) {

        return super.getNameSpace()+"zj/zjlxcenter";
    }

    @RequestMapping("xuangu")
    public String xuangu(HttpServletRequest request) {

        return super.getNameSpace()+"feature/xuangu";
    }

    @RequestMapping("xuangudata")
    public void xuanguData(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject objTmp,obj = new JSONObject();
            JSONArray objArr;
            Map<String,String> params = new HashMap<>();
            Map<String, Object> sfmap,slmap,sdmap,stmap;
            List<Map<String, Object>> lflist = new ArrayList<>(),lmlist,ldlist,ltlist = new ArrayList<>();

            List<SelectFirst> sflist = sfService.query(params);
            for (SelectFirst sf : sflist) {
                stmap = new HashMap<>();
                stmap.put("name", sf.getName());
                if (sf.getFocus() != null)
                    stmap.put("focus", "c".equals(sf.getFocus()) ? "c" : false);
                sfmap = new HashMap<>();
                if (sf.getItems() != null)
                    sfmap.put("items", (sf.getItems() == 0) ? false : true);
                params.put("listCont", sf.getListCont().toString());
                List<SelectList> sllist = slService.query(params);
                lmlist = new ArrayList<>();
                for (SelectList sl : sllist) {
                    slmap = new HashMap<>();
                    slmap.put("pre_tit", sl.getPreTit());
                    slmap.put("name", sl.getName());
                    if (sl.getColsable() != null)
                        slmap.put("colsable", (sl.getColsable() == 0) ? false : true);

                    params.put("ul", sl.getUl());
                    List<SelectDetail> sdlist = sdService.query(params);
                    if (StringUtils.isNotEmpty(sl.getSelectorcache())) {
                        ldlist = new ArrayList<>();
                        for (SelectDetail sd : sdlist) {
                            params.put("ul", sd.getId());
                            List<SelectDetail> sdl = sdService.query(params);
                            if (sdl == null || sdl.size() == 0) continue;
                            sdmap = new HashMap<>();
                            sdmap.put("id", sdl.get(0).getUl());
                            for (SelectDetail sdtmp : sdl) sdtmp.setUl(null);
                            sdmap.put("li", sdl);
                            ldlist.add(sdmap);
                        }
                        if (ldlist.size() > 0) {
                            objArr = new JSONArray();
                            objTmp = new JSONObject();
                            objTmp.put("ul", JSON.parseArray(JSON.toJSONString(ldlist, true)));
                            objArr.add(objTmp);
                            slmap.put("selectorcache", objArr);
                        }
                    }
                    for (SelectDetail sdtmp : sdlist) sdtmp.setUl(null);
                    slmap.put("ul", JSON.parseArray(JSON.toJSONString(sdlist, true)));
                    lmlist.add(slmap);
                }
                sfmap.put("list_cont", JSON.parseArray(JSON.toJSONString(lmlist, true)));
                lflist.add(sfmap);
                ltlist.add(stmap);
            }
            objArr = new JSONArray();
            objTmp = new JSONObject();
            objTmp.put("li", JSON.parseArray(JSON.toJSONString(ltlist, true)));
            objArr.add(objTmp);
            obj.put("tabs", objArr);
            obj.put("cont", JSON.parseArray(JSON.toJSONString(lflist, true)));

            response.getWriter().print(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("notices")
    public String notices(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        String market = params.get("market");
        if (StringUtils.isEmpty(market)) market = "hsa";
        String type = params.get("type");
        if (StringUtils.isEmpty(type)) type = "0";

        request.setAttribute("market", market);
        request.setAttribute("type", type);
        return super.getNameSpace()+"feature/notices";
    }

    @RequestMapping("notices/detail")
    public String notdetail(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        params.put("stype", "a");
        List<StocksInfo> silist = siService.query(params);
        request.setAttribute("stocksInfo", silist.get(0));

        return super.getNameSpace()+"feature/notdetail";
    }

    @RequestMapping("calendar")
    public String calendar(HttpServletRequest request) {

        return super.getNameSpace()+"feature/calendar";
    }

}
