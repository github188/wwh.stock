package cn.hzskt.bdtg.financial.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.financial.domain.OrderCharge;
import cn.hzskt.bdtg.financial.service.FinanceService;
import cn.hzskt.bdtg.financial.service.OrderChargeService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;

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
