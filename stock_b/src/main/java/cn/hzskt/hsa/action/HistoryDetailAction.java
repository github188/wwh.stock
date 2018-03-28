package cn.hzskt.hsa.action;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.hsa.domain.HistoryData;
import cn.hzskt.hsa.domain.HistoryDetail;
import cn.hzskt.hsa.domain.MarketData;
import cn.hzskt.hsa.domain.PlateStock;
import cn.hzskt.hsa.service.HistoryDataService;
import cn.hzskt.hsa.service.HistoryDetailService;
import cn.hzskt.hsa.service.MarketDataService;
import cn.hzskt.hsa.service.PlateStockService;
import cn.hzskt.util.StockUtil;
import cn.hzskt.util.beanutil.ConvertFactory;
import com.zjhcsoft.smartcity.magic.commons.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 
 * @description:HistoryDetail action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/history-detail")
@SuppressWarnings("serial")
public class HistoryDetailAction extends MagicAction<HistoryDetail,HistoryDetailService> {

 @Autowired
 protected PlateStockService pService;

 @Autowired
 protected MarketDataService mService;

 @Autowired
 protected HistoryDataService hService;

 /**
  * 生成记录.
  */
 @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
 public void dataRefresh(String industryId, HttpServletResponse response) {
  try {
   if(StringUtils.isEmpty(industryId)) {
    List<MarketData> list = mService.getStockInfo();
    for (int i = 0; i < list.size(); i++) {
     String[] stockdata = StockUtil.getStockDetail(list.get(i).getCode());
     getHistoryDetail(stockdata);
    }
   } else {
    List<PlateStock> list = pService.getPlateInfo(industryId);
    for (int i = 0; i < list.size(); i++) {
     String[] stockdata = StockUtil.getStockDetail(list.get(i).getCode());
     getHistoryDetail(stockdata);
    }
   }

   printJson(response, messageSuccuseWrap());
  } catch (Exception e) {
   logger.error("save", e);
   printJson(response, messageFailureWrap("保存失败！"));
  }
 }

 private void getHistoryDetail(String[] val) {
  if (val == null) return;
  HistoryDetail o = new HistoryDetail();
  List<HistoryData> list = hService.getHistoryDetinfo(val[0]);
  if (list.get(0) != null) {
   o.setHighestPrice(list.get(0).getHighestPrice());
   o.setLowestPrice(list.get(0).getLowestPrice());
   o.setPressurePrice(list.get(0).getHighestPrice());
   list = hService.getHistoryDt(val[0], DateUtils.format(DateUtils.preYear(new Date()),DateUtils.DATE_FORMAT_STR));
   if (list.size()>0 && list.get(0) != null) {
    o.setSupportPrice(list.get(0).getClosingPrice());
   }
   list = hService.getHistoryDetinfo1(val[0]);
   if (list.get(0) != null) {
    o.setSuspensionDays(list.get(0).getSuspensionDays());
    o.setStartDate(list.get(0).getStartDate());
    o.setEndDate(list.get(0).getEndDate());
   }
  }
  o.setCode(val[0]);
  o.setName(val[1]);
  if (StringUtils.isNotEmpty(val[2])) o.setCurrentPrice(ConvertFactory.convert(Double.TYPE, val[2]));
  if (StringUtils.isNotEmpty(val[3])) o.setFiveWidth(ConvertFactory.convert(Double.TYPE, val[3]));
  if (StringUtils.isNotEmpty(val[4])) o.setTenWidth(ConvertFactory.convert(Double.TYPE, val[4]));
  if (StringUtils.isNotEmpty(val[5])) o.setTwentyWidth(ConvertFactory.convert(Double.TYPE, val[5]));
  if (StringUtils.isNotEmpty(val[6])) o.setCirculationEquity(ConvertFactory.convert(Double.TYPE, val[6]));
  if (StringUtils.isNotEmpty(val[7])) o.setTotalEquity(ConvertFactory.convert(Double.TYPE, val[7]));
  o.setPerProfit(ConvertFactory.convert(Double.TYPE, val[8]));
  o.setNetAssets(ConvertFactory.convert(Double.TYPE, val[9]));
  o.setCapitalFund(ConvertFactory.convert(Double.TYPE, val[10]));
  o.setNetProfit(ConvertFactory.convert(Double.TYPE, val[11]));
  o.setProfitDescribe(StockUtil.cutTrim(val[12],50));
  o.setMainBusiness(StockUtil.cutTrim(val[13],50));
  o.setThePlate(StockUtil.cutTrim(val[14],50));
  o.setMemo(StockUtil.cutTrim(val[15],50));

  entityService.saveOrUpdate(o);
 }

}
