package com.skoo.stock.pub.service;

import com.skoo.stock.pub.domain.ContentJoin;
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
public class  ContentJoinService extends BaseService<ContentJoin> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<ContentJoin> queryPaged(Condition condition) {
		Assert.notNull(condition);
		return super.queryPaged("ContentJoin_custlist", condition);
	}

	/**
	 * 根据条件查询
	 * @param condition
	 * @return
	 */
	public List<ContentJoin> getContentJoinList(Condition condition) {
		Assert.notNull(condition);
		return this.query("ContentJoin_custlist", condition.getMap());
	}
}
