package com.skoo.stock.hsa.service;

import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.PlateStock;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
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
public class  PlateStockService extends BaseService<PlateStock> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<PlateStock> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(PlateStock.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		String industryType = condition.get("industryType");
		if (StringUtils.isNotEmpty(industryType))
			c.add(Restrictions.like("industryType", industryType + "%"));
		String conceptId = condition.get("conceptId");
		if (StringUtils.isNotEmpty(conceptId))
			c.add(Restrictions.eq("conceptId", conceptId));
		return super.queryPaged(condition);
	}

	public List<PlateStock> getPlateInfo(String conceptId) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(PlateStock.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		if (StringUtils.isNotEmpty(conceptId)) c.add(Restrictions.eq("industryType", conceptId));

		return super.query(condition);
	}

	public Long delOrUpdate(PlateStock o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(PlateStock.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		c.add(Restrictions.eq("industryType", o.getIndustryType()));
		c.add(Restrictions.eq("conceptId", o.getConceptId()));
		c.add(Restrictions.eq("code", o.getCode()));
		List<PlateStock> list = super.query(condition);
		if(list != null && list.size() > 0) {
			return Long.parseLong(String.valueOf(super.remove(list.get(0))));
		}

		return super.saveOrUpdate(o);
	}

	public Long saveOrUpdate(PlateStock o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(PlateStock.class);
		c.add(Restrictions.eq("netFlag", Constant.NETFLG));
		c.add(Restrictions.eq("industryType", o.getIndustryType()));
		c.add(Restrictions.eq("conceptId", o.getConceptId()));
		c.add(Restrictions.eq("code", o.getCode()));
		List<PlateStock> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
			//return Long.parseLong(String.valueOf(super.remove(list.get(0))));
		}

		return super.saveOrUpdate(o);
	}

	public int delete_byconceptid(PlateStock o) {
		Assert.notNull(o);
		return super.delete("PlateStock_delete_byconceptid", o);
	}
}
