package com.skoo.stock.hsa.action;

import com.skoo.commons.StringUtils;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.common.service.StockPlateQtz;
import com.skoo.stock.hsa.domain.IndustryData;
import com.skoo.stock.hsa.service.IndustryDataService;
import com.skoo.stock.sys.domain.Dict;
import com.skoo.stock.sys.service.DictService;
import com.skoo.stock.sys.utils.json.JsonUtils;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.StockUtil;
import com.skoo.stock.util.beanutil.ConvertFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @description:IndustryData action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/industry-data")
@SuppressWarnings("serial")
public class IndustryDataAction extends ManAction<IndustryData,IndustryDataService> {

    @Autowired
    protected DictService dService;

    @Autowired
    private StockPlateQtz stockPlateQtz;

    /**
    * 生成记录.
    */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryType, HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(industryType)) {
                printJson(response, messageFailureWrap("请选择板块！"));
                return;
            }
            stockPlateQtz.setPlate(industryType);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
           logger.error("save", e);
           printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void dataRefresh1(String industryType, HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(industryType)) {
                printJson(response, messageFailureWrap("请选择板块！"));
                return;
            }
            List<Dict> list = dService.getDictInfo("industry_type", industryType);
            if(list == null || list.size() < 1) return;
            String sAddr = list.get(0).getMemo();
            if (StringUtils.isEmpty(sAddr)) return;

            LinkedHashMap<String, String[]> map = StockUtil.getStockList("keyName", sAddr);
            if (map == null || map.size() < 1) return;
            entityService.delete_byindustryid(industryType + "%");
            getIndustryData(map, industryType);

            boolean loopFlag = true;
            LinkedHashMap<String, String[]> map1;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int k = 0; k < listName.size(); k++) {
                        sAddr = listName.get(k);
                        if (sAddr.indexOf("<") < 0) continue;
                        sAddr = sAddr.split("<")[1].replace(">","");
                        map1 = StockUtil.getStockList("keyName", sAddr);
                        getIndustryData(map1, industryType);
                    }
                }
            } while (loopFlag);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
           logger.error("save", e);
           printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void getIndustryData(LinkedHashMap<String, String[]> map, String industryType) {
        String[] val;
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));

            IndustryData o = new IndustryData();
            o.setIndustryId(industryType + String.format("%02d",i));
            o.setIndustryName(val[0]);
            o.setStockNum(ConvertFactory.convert(Integer.TYPE, val[1]));
            o.setUdWidth(val[2].replace("%", ""));
            o.setUpNum(ConvertFactory.convert(Integer.TYPE, val[3]));
            o.setUpWidth(val[4].replace("%",""));
            o.setDownNum(ConvertFactory.convert(Integer.TYPE, val[5].replace("%","")));
            o.setDownWidth(val[6].replace("%",""));
            o.setVolume(StockUtil.cvtDouble(val[7]));
            o.setTurnVolume(StockUtil.cvtDouble(val[8].replace("%","")));
            o.setCircuValue(StockUtil.cvtDouble(val[9]));
            o.setNetAddress(val[10] + "|" + String.valueOf((int) Math.ceil(Double.valueOf(val[1]) / 30)));
            entityService.saveOrUpdate(o);
        }
    }

    /**
     * 取得行业信息
     */
    @RequestMapping(value = "getIndustryInfo")
    private void getIndustryInfo(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        try {
            String industryId = request.getParameter("industryId");
            List<IndustryData> lst = entityService.getIndustryInfo(industryId);

            printText(response, JsonUtils.bean2JsonArray(lst, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("getInsTags", e);
            printText(response, messageFailureWrap("数据取得失败！"));
        }
    }
}
