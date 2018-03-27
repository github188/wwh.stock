package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.stk.domain.BasicInfo;
import cn.hzstk.securities.stk.service.BasicInfoService;
import cn.hzstk.securities.stk.service.RxDataService;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.util.FileUtil;
import cn.hzstk.securities.util.HtmlUnitUtil;
import cn.hzstk.securities.util.StkCommUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
*
* @description:BasicInfo action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/basic-info")
@SuppressWarnings({"serial", "JavaDoc"})
public class BasicInfoAction extends MagicAction<BasicInfo,BasicInfoService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    @Autowired
    protected RxDataService rService;
    @Autowired
    ParamService paramService;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String[] group = {"[class=qphox header-title mb7]","#rtp2"};
            String netFlag = paramService.getbyName(ParamKeys.DATA_FROM_NET);
            BasicInfo o = bindEntity(request, entityClass);
            String stockCode = o.getStockCode();
            String[] sData;
            if (StringUtils.isEmpty(stockCode)) {
                List<String> list = rService.getAllStockCode();
                for (String aList : list) {
                    if (Constant.STAR_NET.equals(netFlag)) {
                        sData = StkCommUtil.getStockBasic(aList);
                        getHistoryDetail(sData);
                    } else {
                        sData = HtmlUnitUtil.getEastValue(aList, group);
                        getBasicInfo(sData);
                    }
                }
            } else {
                if (Constant.STAR_NET.equals(netFlag)) {
                    sData = StkCommUtil.getStockBasic(stockCode);
                    getHistoryDetail(sData);
                } else {
                    sData = HtmlUnitUtil.getEastValue(stockCode, group);
                    getBasicInfo(sData);
                }
            }

            boolean loopFlag = true;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (String aListName : listName) {
                        sData = StkCommUtil.getStockBasic(aListName);
                        getHistoryDetail(sData);
                    }
                }
            } while (loopFlag);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    private void getHistoryDetail(String[] val) {
        if (val == null || val.length < 2) return;
        BasicInfo o = new BasicInfo();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setCirculationEquity(val[2]);
        o.setTotalEquity(val[3]);
        o.setPerProfit(val[4]);
        o.setNetAssets(val[5]);
        o.setNdistribProfit(val[6]);
        o.setCapitalFund(val[7]);
        o.setAssetsYield(val[8]);
        o.setMainRevenue(val[9]);
        o.setNetProfit(val[10]);
        o.setProfitDescribe(StkCommUtil.cutTrim(val[11], 50));
        o.setMainBusiness(StkCommUtil.cutTrim(val[12], 50));
        o.setThePlate(StkCommUtil.cutTrim(val[13], 50));
        //o.setMemo(StkCommUtil.cutTrim(val[15],50));

        entityService.saveOrUpdate(o);
    }

    private void getBasicInfo(String[] val) {
        if (val == null || val.length < 2) return;
        BasicInfo o = new BasicInfo();
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setCirculationEquity(StkCommUtil.getSplit(val[16]));
        o.setTotalEquity(StkCommUtil.getSplit(val[14]));
        o.setPerProfit(StkCommUtil.getSplit(val[2]));
        o.setNetAssets(StkCommUtil.getSplit(val[4]));
        o.setNdistribProfit(StkCommUtil.getSplit(val[18]));
        //o.setCapitalFund(val[7]);
        o.setAssetsYield(StkCommUtil.getSplit(val[12]));
        o.setMainRevenue(StkCommUtil.getSplit(val[6]));
        o.setNetProfit(StkCommUtil.getSplit(val[8]));
        o.setOpenDate(StkCommUtil.getSplit(val[19]));
        /*o.setProfitDescribe(StkCommUtil.cutTrim(val[11], 50));
        o.setMainBusiness(StkCommUtil.cutTrim(val[12], 50));
        o.setThePlate(StkCommUtil.cutTrim(val[13], 50));*/
        //o.setMemo(StkCommUtil.cutTrim(val[15],50));

        entityService.saveOrUpdate(o);
    }
}
