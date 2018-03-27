package com.skoo.stock.hsa.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.hsa.domain.SuspensionDetail;
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
public class  SuspensionDetailService extends BaseService<SuspensionDetail> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<SuspensionDetail> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(SuspensionDetail.class);
		//c.add(Restrictions.eq("endDate", ""));
		String code = condition.get("code");
		if (!StringUtils.isEmpty(code))
			c.add(Restrictions.like("code", "%" + code + "%"));
		return super.queryPaged(condition);
	}

	public Long saveOrUpdate(SuspensionDetail o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(SuspensionDetail.class);
		c.add(Restrictions.eq("code", o.getCode()));
		c.add(Restrictions.eq("endDate", ""));
		List<SuspensionDetail> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}
}
