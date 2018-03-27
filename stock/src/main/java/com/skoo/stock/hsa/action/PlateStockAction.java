package com.skoo.stock.hsa.action;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.skoo.commons.StringUtils;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.hsa.domain.IndustryData;
import com.skoo.stock.hsa.domain.PlateStock;
import com.skoo.stock.hsa.service.IndustryDataService;
import com.skoo.stock.hsa.service.PlateStockService;
import com.skoo.stock.util.HtmlUnitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:PlateStock action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/hsa/plate-stock")
@SuppressWarnings("serial")
public class PlateStockAction extends ManAction<PlateStock, PlateStockService> {

    @Autowired
    protected IndustryDataService dService;

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
            List<IndustryData> list;
            list = dService.getIndustryInfo(industryType);
            PlateStock o = new PlateStock();
            o.setNetFlag(Constant.NETFLG);
            o.setIndustryType(industryType);
            entityService.delete_byconceptid(o);

            for (int i = 0; i < list.size(); i++) {
                setPlateStock(list.get(i));
                //LinkedHashMap<String, String[]> map = StockUtil.getStockList(list.get(i).getIndustryId(), list.get(i).getNetAddress());
                //getConceptData(map,list.get(i).getIndustryId());
            }

/*            boolean loopFlag = true;
            LinkedHashMap<String, String[]> map1;
            String sAddr;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int k = 0; k < listName.size(); k++) {
                        sAddr = listName.get(k);
                        if (sAddr.indexOf("<") < 0) continue;
                        sAddr = sAddr.split("<")[1].replace(">","");
                        map1 = StockUtil.getStockList("stock", sAddr);
                        getConceptData(map1, industryType);
                    }
                }
            } while (loopFlag);*/

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void setPlateStock(IndustryData industryData) {
        String[] val;
        //String[] arg = new String[]{Constant.EASTADDR + "BKList.html#notion_0_0?sortRule=0", industryData.getNetAddress(), "#listview"};
        String[] arg = new String[]{Constant.EASTADDR + "BKList.html#notion_0_0?sortRule=0", industryData.getIndustryName(), "#listview"};

        LinkedHashMap<String, String[]> map = HtmlUnitUtil.getDataList(arg);
        for (int i = 1; i < map.size(); i++) {
            PlateStock o = new PlateStock();
            o.setNetFlag(Constant.NETFLG);
            o.setIndustryType(industryData.getIndustryType());
            o.setConceptId(industryData.getIndustryId());
            val = map.get(String.valueOf(i));
            o.setCode(val[1]);

            entityService.saveOrUpdate(o);
        }
    }

    private void getConceptData(LinkedHashMap<String, String[]> map, String industryId) {
        if (map == null || map.size() <= 1) {
            logger.info("读取失败：" + industryId);
            return;
        }
        PlateStock o = new PlateStock();
        o.setConceptId(industryId);
        entityService.delete_byconceptid(o);

        String[] val;
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));

            o.setCode(val[0]);
            entityService.saveOrUpdate(o);
        }
    }

}
