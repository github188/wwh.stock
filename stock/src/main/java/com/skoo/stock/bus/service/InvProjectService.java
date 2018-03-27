package com.skoo.stock.bus.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.bus.domain.InvProject;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  InvProjectService extends BaseService<InvProject> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<InvProject> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(InvProject.class);
		String projectName = condition.get("projectName");
		if (!StringUtils.isEmpty(projectName))
			c.add(Restrictions.like("projectName", "%" + projectName + "%"));
		return super.queryPaged(condition);
	}
}
