package cn.hzskt.bdtg.financial.service;

import cn.hzskt.bdtg.financial.domain.Finance;
import cn.hzskt.bdtg.financial.domain.Withdraw;
import cn.hzskt.bdtg.financial.mapper.WithdrawMapper;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  WithdrawService extends BaseService<Withdraw,WithdrawMapper> {
	
	@Autowired
	FinanceService financeService;
	@Autowired
	AuthSpaceService authSpaceService;
	
    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Withdraw> queryOrder(Map<String, String> paramMap) {
        Example example = new Example(Withdraw.class);
        Example.Criteria criteria = example.createCriteria();
        String username = paramMap.get("username");
        String payAccount = paramMap.get("payAccount");
        String withdrawCash = paramMap.get("withdrawCash");
        String withdrawStatus = paramMap.get("withdrawStatus");
        return mapper.orderByTime(username,payAccount,withdrawCash,withdrawStatus);
    }

    public PageInfo<Withdraw> queryPaged1(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("rows") == null?null:(String)paramMap.get("rows");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;

        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PageHelper.startPage(page, rows);
        List list = this.queryOrder(paramMap);
        return new PageInfo(list);
    }
     
     /**
      * 审核不通过
      * @param paramMap
      * @return
      */
      public boolean auditdenied(String id) {
    	  try{
     	 Withdraw withdraw = get(Long.valueOf(id));
     	 withdraw.setWithdrawStatus(-1);
     	 saveOrUpdate(withdraw);
     	 
     	 Double money = withdraw.getWithdrawCash();
     	//添加用户余额
     	 AuthSpace as = authSpaceService.getbyuid(Integer.valueOf(withdraw.getUid()));
         Double balance = as.getBalance()+money;
         as.setBalance(balance);
         authSpaceService.saveOrUpdate(as);
         
         //生成账户流水
         Finance finance = new Finance();
         finance.setFinaAction("withdraw_fail");
         finance.setFinaCash(money);
         finance.setFinaType("in");
         finance.setOrderId(0);
     	 
       //TODO
//			finance.setUid(user.getId().intValue());
//			finance.setUsername(user.getName());
			
         finance.setUid(1);
         finance.setUsername("admin");
         Date date =new Date();
         finance.setFinaTime(date.getTime()/1000);
         finance.setUserBalance(balance);
         finance.setFinaMem("账户为"+withdraw.getUsername()+"的用户提现失败返还退款");
         financeService.saveOrUpdate(finance);  
         
         return true;
    	 }catch(Exception e){
    		 return false;
    	  }

      }
}
