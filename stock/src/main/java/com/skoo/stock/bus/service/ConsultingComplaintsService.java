package com.skoo.stock.bus.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.bus.domain.ConsultingComplaints;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  ConsultingComplaintsService extends BaseService<ConsultingComplaints> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<ConsultingComplaints> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(ConsultingComplaints.class);
		String type = condition.get("type");
		if (!StringUtils.isEmpty(type))
			c.add(Restrictions.like("type", "%" + type + "%"));
		return super.queryPaged(condition);
	}
}
