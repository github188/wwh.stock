package com.skoo.stock.app.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.app.domain.AppVersions;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  AppVersionsService extends BaseService<AppVersions> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<AppVersions> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(AppVersions.class);
		String iosversion = condition.get("iosversion");
		if (!StringUtils.isEmpty(iosversion))
			c.add(
					Restrictions.like("iosversion", "%" + iosversion
							+ "%"));
		return super.queryPaged(condition);
	}
}
