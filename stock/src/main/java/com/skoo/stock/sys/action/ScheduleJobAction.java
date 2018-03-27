package com.skoo.stock.sys.action;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.service.LoadTask;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.ScheduleJob;
import com.skoo.stock.sys.service.ScheduleJobService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @description:ScheduleJob action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/sys/schedule-job")
@SuppressWarnings("serial")
public class ScheduleJobAction extends ManAction<ScheduleJob,ScheduleJobService> {


 private final static LoadTask loadJob = SpringContextUtil.getBean(LoadTask.class);

 /**
  * 分页查询member列表.
  */
 @RequestMapping(value = "queryPaged")
 @SuppressWarnings("unchecked")
 public void queryPaged(HttpServletRequest request,
                        HttpServletResponse response) {
  try {
   Condition condition = bindCondition(request);
   PageInfo<ScheduleJob> page = entityService.queryPaged(condition);
   /*printText(response, JsonUtils.bean2Json(page, "yyyy-MM-dd"));*/
   printJson(response,
           JsonUtils.bean2Json(page, null, "yyyy-MM-dd", ScheduleJob.class));
  } catch (Exception e) {
   logger.error("queryPaged", e);
  }
 }

 /**
  * 调度
  */
 @RequestMapping(value = "setCron", method = RequestMethod.POST)
 public void setCron(HttpServletResponse response) {
  try {
   loadJob.initTask();

   printJson(response, messageSuccuseWrap());
  } catch (Exception e) {
   logger.error("save", e);
   printJson(response, messageFailureWrap("调度失败！"));
  }
 }
}
