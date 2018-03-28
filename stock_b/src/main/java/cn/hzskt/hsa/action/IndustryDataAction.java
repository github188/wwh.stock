package cn.hzskt.hsa.action;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.hsa.domain.IndustryData;
import cn.hzskt.hsa.service.IndustryDataService;
import cn.hzskt.sys.domain.Dict;
import cn.hzskt.sys.service.DictService;
import cn.hzskt.util.StockUtil;
import cn.hzskt.util.beanutil.ConvertFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class IndustryDataAction extends MagicAction<IndustryData,IndustryDataService> {

    @Autowired
    protected DictService dService;

    /**
    * 生成记录.
    */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryType, HttpServletResponse response) {
        try {
            List<Dict> list = dService.getDictInfo("industry_type", industryType);
            if(list == null || list.size() < 1) return;
            String sAddr = list.get(0).getMemo();

            LinkedHashMap<String, String[]> map = StockUtil.getStockList("keyName", sAddr);
            if (map == null || map.size() < 1) return;
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
                o.setDownNum(ConvertFactory.convert(Integer.TYPE, val[5]));
                o.setDownWidth(val[6].replace("%",""));
                o.setVolume(ConvertFactory.convert(Double.TYPE, val[7]));
                o.setTurnVolume(ConvertFactory.convert(Double.TYPE, val[8]));
                o.setCircuValue(ConvertFactory.convert(Double.TYPE, val[9]));
                o.setNetAddress(val[10] + "|" + String.valueOf((int) Math.ceil(Double.valueOf(val[1]) / 30)));
                entityService.saveOrUpdate(o);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
           logger.error("save", e);
           printJson(response, messageFailureWrap("保存失败！"));
        }
    }

}
