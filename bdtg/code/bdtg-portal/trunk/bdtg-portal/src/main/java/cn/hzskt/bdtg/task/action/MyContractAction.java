package cn.hzskt.bdtg.task.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.financial.service.FinanceService;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.task.domain.Contract;
import cn.hzskt.bdtg.task.service.ContractService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("owner")
public class MyContractAction extends BaseMagicAction {

	@Autowired
	private AuthSpaceService authSpaceService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinanceService financeService;
	
	@RequestMapping("constractlist")
	public String constractList(HttpServletRequest request) throws Exception {
		Map params = this.getParameterMap(request);
		PageInfo<Contract> page = contractService.queryPaged(params);
		request.setAttribute("pagnation", page);
		return "task/mytask/constractlist";
	}
	
	@RequestMapping("contractloan")
	public void loan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		Contract bean = new Contract();
		bean.setId(Long.parseLong(id));
		bean.setPyStatus("2");		//将记录置成已放款状态
		bean.setSubTime(new Date());
		contractService.saveOrUpdate(bean);
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.printJson(response, JSON.toJSONString(params));
	}
	
	@RequestMapping("paycontract")
	public String paycontract(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		
		Contract bean = contractService.get(Long.parseLong(id));
		request.setAttribute("model", bean);
		
		return "task/mytask/constractpay";
	}
	
	@RequestMapping("contractPay")
	public void orderPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = PortalUtil.getUser(request);
		
		String id = request.getParameter("id");
		
		Contract bean = contractService.get(Long.parseLong(id));
		
		double balance = authSpaceService.getuserbalance(user.getId());
		Double cash = bean.getPyCash();

		if(cash > balance){
			result.put("success", false);
			result.put("type", "2");
			response.getWriter().write(JSON.toJSONString(result));
			return;
		}
		
		if(cash > 0){
			financeService.balancepay(cash, user, "job_cash", Integer.parseInt(id), "task", "业主分期支付：" + cash, 0);
		}
		
		Contract tmp = new Contract();
		tmp.setId(Long.parseLong(id));
		tmp.setPyStatus("1");		//将记录置成已放款状态
		tmp.setCashTime(new Date());
		contractService.saveOrUpdate(tmp);
		
		result.put("success", true);
		response.getWriter().write(JSON.toJSONString(result));
	}
	
	
}
