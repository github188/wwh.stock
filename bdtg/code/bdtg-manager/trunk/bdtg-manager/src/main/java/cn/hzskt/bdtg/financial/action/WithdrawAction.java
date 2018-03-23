package cn.hzskt.bdtg.financial.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.financial.domain.Withdraw;
import cn.hzskt.bdtg.financial.service.WithdrawService;

/**
*
* @description:Withdraw action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial/withdraw")
@SuppressWarnings("serial")
public class WithdrawAction extends MagicAction<Withdraw,WithdrawService> {

	@RequestMapping(value = "queryPaged")
	public void queryPaged(HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			PageInfo<?> page = entityService.queryPaged1(getParameterMap(request));
			JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
			o.put("rows", o.get("list"));
			o.remove("list");
			o.put("totalPageCount", o.get("lastPage"));
			o.put("countPerPage", o.get("pageSize"));
			o.put("currentPage",o.get("pageNum"));
			printJson(response,o.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 @RequestMapping(value = "audit_granted",method = RequestMethod.POST)
	    public void auditgranted(HttpServletRequest request,
	                       HttpServletResponse response) {
		 try {
	            String id = request.getParameter("id");
	            Withdraw withdraw = entityService.get(Long.valueOf(id));
	       	 	withdraw.setWithdrawStatus(1);
	       	 	
	       	 	//TODO
	       	 	entityService.saveOrUpdate(withdraw);
	       	 	printJson(response, messageSuccuseWrap());
		 } catch (Exception e) {
	            logger.error("audit", e);
	            printJson(response, messageFailureWrap("审核通过操作失败！"));
	        }
	 }
	 
	 @RequestMapping(value = "audit_denied",method = RequestMethod.POST)
	    public void auditdenied(HttpServletRequest request,
	                       HttpServletResponse response) {
		 try {
	            String id = request.getParameter("id");
	            entityService.auditdenied(id);
	            printJson(response, messageSuccuseWrap());
		 } catch (Exception e) {
	            logger.error("audit", e);
	            printJson(response, messageFailureWrap("审核不通过操作失败！"));
	        }
	 }
}
