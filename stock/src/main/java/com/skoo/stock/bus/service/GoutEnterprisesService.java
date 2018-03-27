package com.skoo.stock.bus.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.bus.domain.GoutEnterprises;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  GoutEnterprisesService extends BaseService<GoutEnterprises> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<GoutEnterprises> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(GoutEnterprises.class);
		String numberOfInvestmentProjects = condition.get("numberOfInvestmentProjects");
		if (!StringUtils.isEmpty(numberOfInvestmentProjects))
			c.add(Restrictions.like("numberOfInvestmentProjects", "%" + numberOfInvestmentProjects + "%"));
		return super.queryPaged(condition);
	}

	public List getProvinces() {
		List l = this.getSqlSession().selectList("mst_nation_area_provinces");
		return l;
	}

	public List getCitiesByPid(String pid) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("pid",Integer.parseInt(pid));
		List l = this.getSqlSession().selectList("mst_nation_area_cities",m);
		return l;
	}

	public List getDistrictsByCid(String cid) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("cid",Integer.parseInt(cid));
		List l = this.getSqlSession().selectList("mst_nation_area_districts",m);
		return l;
	}


	public String getCountries() {
		List l = this.getSqlSession().selectList("mst_nation_area_countries");
		Object o = l.get(0);
		Map<String,Object> m = (Map<String, Object>) o;
		return (String) m.get("json");
	}

	public String getRegionById(String id) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", id);
		List l = this.getSqlSession().selectList("mst_nation_area_getRegionById",m);
		Object o = l.get(0);
		JSONObject jo = JSONObject.fromObject(o);
		return jo.toString();
	}
}
