package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.AuthBank;
import cn.hzstk.securities.financial.domain.Finance;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.domain.Withdraw;
import cn.hzstk.securities.financial.mapper.FinanceMapper;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.domain.User;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  FinanceService extends BaseService<Finance,FinanceMapper> {
	  @Autowired
	  WithdrawService withdrawService;
	  @Autowired
	   AuthSpaceService authSpaceService;
    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Finance> query(Map<String, String> paramMap) {
        Example example = new Example(Finance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid=paramMap.get("userid");
        String id=paramMap.get("intFinaId");
        String ord=paramMap.get("ord");
        criteria.andEqualTo("uid", uid);
        if(!StringUtils.isEmpty(id)){
            criteria.andEqualTo("id", id);
            return mapper.selectByExample(example);
        }
        if(!StringUtils.isEmpty(ord)){
            switch (ord){
                case "1" :return  mapper.selectByExample(example);
                case "2" :return  mapper.orderByIdAll(uid);
                case "3" :return  mapper.orderByTimeAllUp(uid);
                case "4" :return  mapper.orderByTimeAllDown(uid);
            }
        }
        return mapper.selectByExample(example);
    }

    public List<?> queryin(Map<String, String> paramMap) {
        Example example = new Example(Finance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid=paramMap.get("userid");
        String id=paramMap.get("intFinaId");
        String ord=paramMap.get("ord");
        criteria.andEqualTo("uid", uid);
        criteria.andEqualTo("finaType", "in");
        if(!StringUtils.isEmpty(id)){
            criteria.andEqualTo("id", id);
            return mapper.selectByExample(example);
         }
        if(!StringUtils.isEmpty(ord)){
            switch (ord){
                case "1":
                    return mapper.selectByExample(example);
                case "2":return  mapper.orderByIdDown("in", uid);
                case "3":
                    return  mapper.orderByTimeUp("in", uid);
                case "4" :
                    return  mapper.orderByTimeDown("in", uid);
            }
        }
        return mapper.selectByExample(example);
    }

    public List<?> queryout(Map<String, String> paramMap) {
        Example example = new Example(Finance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid=paramMap.get("userid");
        String id=paramMap.get("intFinaId");
        String ord=paramMap.get("ord");
        criteria.andEqualTo("uid", uid);
        criteria.andEqualTo("finaType", "out");
        if(!StringUtils.isEmpty(id)){
            criteria.andEqualTo("id", id);
            return mapper.selectByExample(example);
        }
        if(!StringUtils.isEmpty(ord)){
            switch (ord){
                case "1" :return  mapper.selectByExample(example);
                case "2":return  mapper.orderByIdDown("out", uid);
                case "3":
                    return  mapper.orderByTimeUp("out", uid);
                case "4" :
                    return  mapper.orderByTimeDown("out", uid);
            }
        }
        return mapper.selectByExample(example);
    }


    public PageInfo<?> queryPaged1(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("pageSize") == null?null:(String)paramMap.get("pageSize");
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
        String key=paramMap.get("key");
        List list=new ArrayList();
        switch (key){
            case "0":list = this.query(paramMap);break;
            case "1":list = this.queryin(paramMap);break;
            case "2":list = this.queryout(paramMap);break;
        }

        return new PageInfo(list);
    }
    
    public Double submit(Withdraw withdraw,Integer id,String name){

		try {
			withdraw.setWithdrawStatus(0);
			withdraw.setUid(id);
			withdraw.setUsername(name);
			withdraw.setApplicTime(new Date());
			
			
			withdrawService.saveOrUpdate(withdraw);//保存未审核提现数据
			
			
			//余额扣除
//		List<AuthSpace> spaces = authSpaceService.querybyuserId(user.getId());
			List<AuthSpace> spaces = authSpaceService.querybyuserId(id.longValue());
			AuthSpace space = new AuthSpace();
			if(spaces.size()>0){
				 space = spaces.get(0);
			}
			Double banlance = space.getBalance()-withdraw.getWithdrawCash();
			space.setBalance(banlance);
			authSpaceService.saveOrUpdate(space);
			
			
			//生成流水明细
			Finance finance = new Finance();
			finance.setFinaAction("withdraw");
			finance.setFinaCash(withdraw.getWithdrawCash());
			finance.setFinaType("out");
			finance.setOrderId(0);
			finance.setUid(id);
			finance.setUsername(name);
			finance.setObjType("withdraw");
			finance.setObjId(withdraw.getId().intValue());
			Date date =new Date();
			finance.setFinaTime(date.getTime()/1000);
			finance.setUserBalance(banlance);
			finance.setFinaMem(withdraw.getPayUsername()+"用户提现");
			saveOrUpdate(finance);
			

			
			return banlance;
		} catch (Exception e) {
			return null;
		}
    }
    
    
    /**
     * 余额支付
     * @param cash 支付金额
     * @param user 当前用户
     * @param action 收支说明 发布任务：pub_task 任务中标：task_bid 任务失败：task_fail
     * @param objtype 对象类型 任务为：task
     * @param objid 对象编号
     * @param orderid 订单编号
     * @param fina_mem 财务去向说明
     * @return
     */
     public boolean balancepay(Double cash,User user,String action,Integer objid,String objtype,String fina_mem,Integer orderid) {
    	 try {
			List<AuthSpace> spaces = authSpaceService.querybyuserId(user.getId());
				AuthSpace space = new AuthSpace();
				if(spaces.size()>0){
					 space = spaces.get(0);
				}else{
					return false;
				}
				Double banlance = space.getBalance()-cash;
				if(banlance<0){
					return false;
				}
				space.setBalance(banlance);
				authSpaceService.saveOrUpdate(space);
				
				
				//生成流水明细
				Finance finance = new Finance();
				finance.setFinaAction(action);
				finance.setFinaCash(cash);
				finance.setFinaType("out");
				finance.setOrderId(orderid);
				finance.setUid(user.getId().intValue());
				finance.setUsername(user.getName());
				finance.setObjType(objtype);
				finance.setObjId(objid);
				Date date =new Date();
				finance.setFinaTime(date.getTime()/1000);
				finance.setUserBalance(banlance);
				finance.setFinaMem(fina_mem);
				saveOrUpdate(finance);
				
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	 return false;
    	 
     }
     
}
