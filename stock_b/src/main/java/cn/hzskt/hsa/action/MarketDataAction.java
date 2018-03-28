package cn.hzskt.hsa.action;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.hsa.domain.MarketData;
import cn.hzskt.hsa.domain.PlateStock;
import cn.hzskt.hsa.service.MarketDataService;
import cn.hzskt.hsa.service.PlateStockService;
import cn.hzskt.util.StockUtil;
import cn.hzskt.util.beanutil.ConvertFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @description:MarketData action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/market-data")
@SuppressWarnings("serial")
public class MarketDataAction extends MagicAction<MarketData,MarketDataService> {

    @Autowired
    protected PlateStockService pService;

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String industryId, HttpServletResponse response) {
        try {
            if(StringUtils.isEmpty(industryId)) {
                LinkedHashMap<String, String[]> map = StockUtil.getStockList("stock", null);
                getMarketData(map);
            } else {
                List<PlateStock> list = pService.getPlateInfo(industryId);
                for (int i = 0; i < list.size(); i++) {
                    LinkedHashMap<String, String[]> map =  new LinkedHashMap<>();
                    map.put("0",null);
                    map.put("1",StockUtil.getStockstar(list.get(i).getCode()));
                    getMarketData(map);
                }
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    private void getMarketData(LinkedHashMap<String, String[]> map) {
        if (map == null || map.size() <= 1) return;
        String[] val;
        for (int i = 1; i < map.size(); i++) {
            val = map.get(String.valueOf(i));

            MarketData o = new MarketData();
            o.setCode(val[0]);
            o.setName(val[1]);
            o.setLatestPrice(ConvertFactory.convert(Double.TYPE, val[2]));
            o.setUdWidth(ConvertFactory.convert(Double.TYPE, val[3].replace("%", "")));
            o.setUdAmount(ConvertFactory.convert(Double.TYPE, val[4]));
            o.setFiveWidth(val[5]);
            if (StringUtils.isNotEmpty(val[6])) o.setVolume(ConvertFactory.convert(Double.TYPE, val[6]));
            if (StringUtils.isNotEmpty(val[7])) o.setTurnVolume(ConvertFactory.convert(Double.TYPE, val[7]));
            o.setTurnoverRate(val[8]);
            o.setAmplitude(val[9]);
            if (StringUtils.isNotEmpty(val[10])) o.setVolumeRatio(ConvertFactory.convert(Double.TYPE, val[10]));
            if (StringUtils.isNotEmpty(val[11])) o.setCommittee(ConvertFactory.convert(Double.TYPE, val[11]));
            o.setPeRatio(ConvertFactory.convert(Double.TYPE, val[12]));
            entityService.saveOrUpdate(o);
        }
    }

}
