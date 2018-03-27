package com.skoo.stock.hsa.service;

import com.skoo.common.SystemConfig;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.IndustryData;
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
public class  IndustryDataService extends BaseService<IndustryData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<IndustryData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(IndustryData.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		String industryType = condition.get("industryType");
		if (StringUtils.isNotEmpty(industryType))
			c.add(Restrictions.eq("industryType", industryType));
		String code = condition.get("industryId");
		if (StringUtils.isNotEmpty(code))
			c.add(Restrictions.eq("industryId", code));
		return super.queryPaged(condition);
	}

	public List<IndustryData> getIndustryInfo(String industryType) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(IndustryData.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		c.add(Restrictions.eq("industryType", industryType));

		return super.query(condition);
	}

	public List<IndustryData> getIndustryInfo(IndustryData o) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(IndustryData.class);
		if (StringUtils.isNotEmpty(o.getIndustryId())) c.add(Restrictions.eq("industryId", o.getIndustryId()));
		c.add(Restrictions.eq("netFlag", o.getNetFlag()));
		c.add(Restrictions.eq("industryType", o.getIndustryType()));

		return super.query(condition);
	}

	public Long saveOrUpdate(IndustryData o) {
		Assert.notNull(o);
		List<IndustryData> list = getIndustryInfo(o);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}

	public int delete_byindustryid(String industryId) {
		Assert.notNull(industryId);
		return super.delete("IndustryData_delete_byindustryid", industryId);
	}
}
