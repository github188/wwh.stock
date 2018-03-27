package com.skoo.stock.sys.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.sys.domain.ScheduleJob;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  ScheduleJobService extends BaseService<ScheduleJob> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<ScheduleJob> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(ScheduleJob.class);
		String jobId = condition.get("jobId");
		if (!StringUtils.isEmpty(jobId))
			c.add(Restrictions.like("jobId", "%" + jobId + "%"));
		return super.queryPaged(condition);
	}
}
