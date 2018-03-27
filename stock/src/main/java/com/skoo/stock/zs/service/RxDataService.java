package com.skoo.stock.zs.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.zs.domain.RxData;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  RxDataService extends BaseService<RxData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<RxData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(RxData.class);
		String stockCode = condition.get("stockCode");
		if (!StringUtils.isEmpty(stockCode))
			c.add(Restrictions.like("stockCode", "%" + stockCode + "%"));
		return super.queryPaged(condition);
	}
}
