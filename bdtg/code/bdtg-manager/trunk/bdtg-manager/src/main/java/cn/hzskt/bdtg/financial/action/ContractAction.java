package cn.hzskt.bdtg.financial.action;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.financial.domain.Finance;
import cn.hzskt.bdtg.financial.service.FinanceService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.task.domain.Bid;
import cn.hzskt.bdtg.task.domain.Contract;
import cn.hzskt.bdtg.task.service.BidService;
import cn.hzskt.bdtg.task.service.ContractService;

/**
*
* @description:Contract action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial/contract")
@SuppressWarnings("serial")
public class ContractAction extends MagicAction<Contract,ContractService> {
	@Autowired
	AuthSpaceService authSpaceService;
	@Autowired
	FinanceService financeService;
	@Autowired
	BidService bidService;
	 @RequestMapping(value = "confirm",method = RequestMethod.POST)
	    public void confirm(HttpServletRequest request,
	                       HttpServletResponse response) {
	        try {
	            String id = request.getParameter("id");
	            Contract contract= entityService.get(Long.valueOf(id));
	            contract.setPyStatus("3");
	            Integer tid = contract.getTkId();
	            entityService.saveOrUpdate(contract);
	            
	            //给中标人打钱
	            Bid bid = bidService.getbytid(tid);
	            Integer userid = bid.getUid();
	            AuthSpace as = authSpaceService.getbyuid(userid);
	            Double balance = as.getBalance();
	            balance = balance+contract.getPyCash();
	            as.setBalance(balance);
	            authSpaceService.saveOrUpdate(as);
	            
	            //生成明细
	            Finance finance =new Finance();
	            finance.setFinaAction("job_loan");
	            finance.setFinaCash(contract.getPyCash());
	            finance.setFinaMem("向"+as.getUserName()+"放款");
	            Date date = new Date();
	            finance.setFinaTime(date.getTime()/1000);
	            finance.setFinaType("in");
	            finance.setObjType("task_contract");
	            finance.setObjId(contract.getId().intValue());
	            finance.setUserBalance(balance);
	            finance.setUsername(as.getUserName());
	            finance.setUid(as.getUserId());
	            financeService.saveOrUpdate(finance);
	            
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("loan", e);
	            printJson(response, messageFailureWrap("放款失败！"));
	        }
	    }
}
