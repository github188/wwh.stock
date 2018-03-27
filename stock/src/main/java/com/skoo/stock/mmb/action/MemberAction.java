package com.skoo.stock.mmb.action;

import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.mmb.domain.Member;
import com.skoo.stock.mmb.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @description:Member action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/mmb/member")
@SuppressWarnings("serial")
public class MemberAction extends ManAction<Member,MemberService> {

 /**
  * 分页查询member列表.
  */
 @RequestMapping(value = "queryPaged")
 @SuppressWarnings("unchecked")
 public void queryPaged(HttpServletRequest request,
                        HttpServletResponse response) {
  try {
   Condition condition = bindCondition(request);
   PageInfo<Member> page = entityService.queryPaged(condition);
   /*printText(response, JsonUtils.bean2Json(page, "yyyy-MM-dd"));*/
   printJson(response,
           JsonUtils.bean2Json(page, null, "yyyy-MM-dd", Member.class));
  } catch (Exception e) {
   logger.error("queryPaged", e);
  }
 }

 @RequestMapping("examination_passed")
 public void examination_passed(HttpServletRequest request,HttpServletResponse response) throws Exception {
  try {
   String ids = request.getParameter("ids");
   for (String id : ids.split(",")) {
    entityService.examination_passed(Long.parseLong(id));
    String content = "id=" + id;
    operateLogService.wirteLog(request, "会员审核通过", content);
   }
   printText(response, messageSuccuseWrap());
  } catch (Exception e) {
   logger.error("delete", e);
   printText(response, messageFailureWrap("审核失败！"));
  }
 }
}
