package cn.hzstk.securities.financial.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzstk.securities.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.service.FinanceService;
import cn.hzstk.securities.financial.service.OrderChargeService;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;

/**
*
* @description:OrderCharge action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial/order-charge")
@SuppressWarnings("serial")
public class OrderChargeAction extends MagicAction<OrderCharge,OrderChargeService> {
	@Autowired
	FinanceService financeService;
	@Autowired
	AuthSpaceService authSpaceService;

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

	 @RequestMapping(value = "confirm",method = RequestMethod.POST)
	    public void audit(HttpServletRequest request,
	                       HttpServletResponse response) {
	        try {
	            String id = request.getParameter("id");
	            entityService.audit(id);        
	              
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("audit", e);
	            printJson(response, messageFailureWrap("审核失败！"));
	        }
	    }
}
