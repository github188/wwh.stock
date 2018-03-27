package com.skoo.stock.sys.service;

import com.skoo.stock.sys.domain.Sysdept;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class SysdeptService extends BaseService<Sysdept> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Sysdept> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Sysdept.class);
		String upId = condition.get("upId");
		if (!StringUtils.isEmpty(upId))
			c.add(Restrictions.like("upId", "%" + upId + "%"));
		return super.queryPaged(condition);
	}

	/**
	 * 根据条件查询分页
	 * @param map
	 * @return
	 */
	public List<Map> getDeptTree(Map map) {
		Assert.notNull(map);
		return this.manQuery("Sysdept_tree_all", map);
	}
}
