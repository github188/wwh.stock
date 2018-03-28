package cn.hzskt.hsa.action;

import cn.hzskt.hsa.domain.HistoryData;
import cn.hzskt.hsa.domain.MarketData;
import cn.hzskt.hsa.service.MarketDataService;
import cn.hzskt.util.FileUtil;
import cn.hzskt.util.StockUtil;
import cn.hzskt.util.beanutil.ConvertFactory;
import com.zjhcsoft.smartcity.magic.common.SystemConfig;
import com.zjhcsoft.smartcity.magic.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.hsa.domain.HistoryData;
import cn.hzskt.hsa.service.HistoryDataService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description:HistoryData action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/history-data")
@SuppressWarnings("serial")
public class HistoryDataAction extends MagicAction<HistoryData,HistoryDataService> {

 @Autowired
 protected MarketDataService mService;

 /**
  * 保存单条Dictionary记录.
  */
 @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
 public void dataRefresh(String code, HttpServletResponse response) {
  try {
   if (StringUtils.isNotEmpty(code)) {
     getHistoryData(code);
   } else {
    List<String> listName = FileUtil.readStockFile("test_in.log");
    if (listName == null || listName.size()==0) {
     List<MarketData> list = mService.getAll();
     for (int i = 0; i < list.size(); i++) {
      getHistoryData(list.get(i).getCode());
     }
    } else {
     for (int i = 0; i < listName.size(); i++) {
      getHistoryData(listName.get(i));
     }
     LinkedHashMap<String, String> map = new LinkedHashMap<>();
     FileUtil.writeFile(map,"test_in.log");
    }
   }

   printJson(response, messageSuccuseWrap());
  } catch (Exception e) {
   logger.error("save", e);
   printJson(response, messageFailureWrap("保存失败！"));
  }
 }

 private void getHistoryData(String code) {
  String sAddr;
  if (code.trim().length()>6) {
   sAddr = code.split("<")[1].split("&")[0] + "&pageid=1|17";
   code = sAddr.split("code=")[1].split("&")[0];
  } else {
   sAddr = SystemConfig.INSTANCE.getValue("historyAddress") + code + "&pageid=1|17";
   List<HistoryData> list = entityService.getHistoryInfo(code);
   if (list != null && list.size() > 0) {
    String dt = list.get(0).getDt();
    sAddr = sAddr + ":" + dt;
   }
  }
  LinkedHashMap<String, String[]> map = StockUtil.getStockList(code, sAddr);
  if (map == null || map.size() <= 1) return;
  String[] val;
  for (int i = map.size() - 1; i > 0; i--) {
   val = map.get(String.valueOf(i));

   HistoryData o = new HistoryData();
   o.setCode(code);
   o.setDt(val[0]);
   o.setClosingPrice(ConvertFactory.convert(Double.TYPE, val[1]));
   o.setUdWidth(val[2]);
   o.setTurnoverRate(val[3]);
   o.setVolume(ConvertFactory.convert(Double.TYPE, val[4]));
   o.setTurnVolume(ConvertFactory.convert(Double.TYPE, val[5]));
   o.setInflowFund(ConvertFactory.convert(Double.TYPE, val[6]));
   o.setTradeBalance(ConvertFactory.convert(Double.TYPE, val[7]));
   o.setFundDiff(ConvertFactory.convert(Double.TYPE, val[8]));
   o.setNetInflowRate(val[9]);
   entityService.saveOrUpdate(o);
  }
 }

}
