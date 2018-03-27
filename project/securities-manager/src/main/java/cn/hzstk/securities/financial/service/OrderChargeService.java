package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.Finance;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.domain.Withdraw;
import cn.hzstk.securities.financial.mapper.OrderChargeMapper;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class OrderChargeService extends
		BaseService<OrderCharge, OrderChargeMapper> {
	@Autowired
	FinanceService financeService;
	@Autowired
	AuthSpaceService authSpaceService;

	/**
	 * 根据条件查询分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<OrderCharge> queryOrder(Map<String, String> paramMap) {
		Example example = new Example(Withdraw.class);
		String username = paramMap.get("username");
		String orderType = paramMap.get("orderType");
		String orderStatus = paramMap.get("orderStatus");


		return mapper.orderByTime(username,orderType,orderStatus);
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
	 * 审核
	 * 
	 * @param String
	 * @return
	 */
	public boolean audit(String id) {
		try {
			OrderCharge o = get(Long.valueOf(id));
			o.setOrderStatus("ok");
			saveOrUpdate(o);

			Double money = o.getPayMoney();
			// 添加用户余额
			List<AuthSpace> ases = authSpaceService.getbyuid(Long.valueOf(o
					.getUid()));
			AuthSpace as = ases.get(0);
			Double bmoney =as.getBalance();
			if(bmoney==null){
				bmoney=0.0;
			}
			Double balance =  bmoney + money;
			as.setBalance(balance);
			authSpaceService.saveOrUpdate(as);

			// 生成账户流水
			Finance finance = new Finance();
			finance.setFinaAction("offline_charge");
			finance.setFinaCash(money);
			finance.setFinaType("in");
			finance.setOrderId(0);
			 finance.setUid(Integer.valueOf(o.getUid()));
			 finance.setUsername(as.getName());

			finance.setObjType("offline_charge");
			Date date = new Date();
			finance.setFinaTime(date.getTime()/1000);
			finance.setUserBalance(balance);
			finance.setFinaMem("线下充值");
			financeService.saveOrUpdate(finance);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
