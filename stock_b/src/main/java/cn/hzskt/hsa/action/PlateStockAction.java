package cn.hzskt.hsa.action;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.hsa.domain.IndustryData;
import cn.hzskt.hsa.domain.PlateStock;
import cn.hzskt.hsa.service.IndustryDataService;
import cn.hzskt.hsa.service.PlateStockService;
import cn.hzskt.util.StockUtil;
import com.zjhcsoft.smartcity.magic.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @description:PlateStock action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/hsa/plate-stock")
@SuppressWarnings("serial")
public class PlateStockAction extends MagicAction<PlateStock,PlateStockService> {

  @Autowired
  protected IndustryDataService dService;

  /**
   * 生成记录.
   */
  @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
  public void dataRefresh(String conceptId, HttpServletResponse response) {
   try {
    List<IndustryData> list;
    if (StringUtils.isNotEmpty(conceptId))
      list = dService.getIndustryInfo(conceptId);
    else
      list = dService.getAll();
    for (int i = 0; i < list.size(); i++) {
      getConceptData(list.get(i));
    }

    printJson(response, messageSuccuseWrap());
   } catch (Exception e) {
    logger.error("save", e);
    printJson(response, messageFailureWrap("保存失败！"));
   }
  }

  private void getConceptData(IndustryData industryData) {
   LinkedHashMap<String, String[]> map = StockUtil.getStockList(industryData.getIndustryId(), industryData.getNetAddress());
   if (map == null || map.size() <= 1) {
    logger.info("读取失败："+industryData.getIndustryId());
    return;
   }
   String[] val;
   for (int i = 1; i < map.size(); i++) {
    val = map.get(String.valueOf(i));

    PlateStock o = new PlateStock();
    o.setConceptId(industryData.getIndustryId());
    o.setCode(val[0]);
    entityService.saveOrUpdate(o);
   }
  }

 }
