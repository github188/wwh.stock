package com.skoo.stock.zs.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.zs.domain.HqDetails;
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
public class  HqDetailsService extends BaseService<HqDetails> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<HqDetails> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(HqDetails.class);
		String dt = condition.get("dt");
		if (!StringUtils.isEmpty(dt))
			c.add(Restrictions.like("dt", "%" + dt + "%"));
		/*c.add(Restrictions.between("turnOver", "0.001", "100.00"));*/
		return super.queryPaged(condition);
	}

	public Long saveOrUpdate(HqDetails o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HqDetails.class);
		c.add(Restrictions.eq("stockCode", o.getStockCode()));
		c.add(Restrictions.eq("dt", o.getDt()));
		List<HqDetails> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}
}
