package com.skoo.stock.hsa.service;

import com.skoo.common.SystemConfig;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.HistoryData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  HistoryDataService extends BaseService<HistoryData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<HistoryData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(HistoryData.class);
		String code = condition.get("code");
		if (StringUtils.isNotEmpty(code))
			c.add(Restrictions.eq("code", code));
		else
			c.add(Restrictions.eq("code", Constant.DEFAULTSTOCK));
		return super.queryPaged(condition);
	}

	public List<HistoryData> getHistoryInfo(String code) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", code));

		return super.query(condition);
	}

	public String getHistoryMaxDt() {
		return super.selectOne("HistoryData_get_maxdt",null);
	}

	public List<HistoryData> getHistoryDt(String code,String dt) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", code));
		c.add(Restrictions.between("dt", "2014-08-23", dt));

		return super.query(condition);
	}

	public List<HistoryData> getHistoryDetinfo(String code) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("month", -2);

		return super.query("HistoryData_get_by_group", map);
	}

	public List<HistoryData> getHistoryDetinfo1(String code) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("month", -6);

		return super.query("HistoryData_get_by_group1", map);
	}

	public Long saveOrUpdate(HistoryData o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", o.getCode()));
		c.add(Restrictions.eq("dt", o.getDt()));
		List<HistoryData> list = super.query(condition);
		if(list != null && list.size() > 0) {
/*
			String dt = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_STR);
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (hour <= 15 && dt.equals(o.getDt())) {
				o.setId(list.get(0).getId());
			} else {
				return null;
			}
*/
			super.updateSelective(o, condition);
			return null;
		} else {
			return super.saveOrUpdate(o);
		}
	}
}
