package com.skoo.stock.hsa.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.hsa.domain.StockNew;
import com.skoo.stock.util.StockUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  StockNewService extends BaseService<StockNew> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<StockNew> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(StockNew.class);
		String code = condition.get("code");
		if (!StringUtils.isEmpty(code))
			c.add(Restrictions.like("code", "%" + code + "%"));
		return super.queryPaged(condition);
	}

	public Long saveOrUpdate(StockNew o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(StockNew.class);
		c.add(Restrictions.eq("code", o.getCode()));
		c.add(Restrictions.eq("netFlag", o.getNetFlag()));
		List<StockNew> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
			//if (com.skoo.commons.StringUtils.isEmpty(o.getTurnoverRate())) {
				o.setFiveWidth(String.format("%.2f", StockUtil.calcWidth(o.getLatestPrice(), list.get(0).getLatestPrice(), o.getFiveWidth())));
				//LOG.info(o.getCode()+"|"+o.getLatestPrice()+"|"+o.getUdWidth()+"|"+o.getUdAmount()+"|"+o.getTurnVolume()+"|"+o.getFiveWidth());
			//}
		}

		return super.saveOrUpdate(o);
	}
}
