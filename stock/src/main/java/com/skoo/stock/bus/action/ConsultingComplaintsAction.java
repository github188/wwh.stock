package com.skoo.stock.bus.action;

import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.mmb.domain.Member;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.bus.domain.ConsultingComplaints;
import com.skoo.stock.bus.service.ConsultingComplaintsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @description:ConsultingComplaints action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/bus/consulting-complaints")
@SuppressWarnings("serial")
public class ConsultingComplaintsAction extends ManAction<ConsultingComplaints,ConsultingComplaintsService> {


 @RequestMapping(value = "queryPaged")
 @SuppressWarnings("unchecked")
 public void queryPaged(HttpServletRequest request,
                        HttpServletResponse response) {
  try {
   Condition condition = bindCondition(request);
   PageInfo<ConsultingComplaints> page = entityService.queryPaged(condition);
   /*printText(response, JsonUtils.bean2Json(page, "yyyy-MM-dd"));*/
   printJson(response,
           JsonUtils.bean2Json(page, null, "yyyy-MM-dd", ConsultingComplaints.class));
  } catch (Exception e) {
   logger.error("queryPaged", e);
  }
 }

}
