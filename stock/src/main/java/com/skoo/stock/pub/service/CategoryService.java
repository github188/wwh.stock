package com.skoo.stock.pub.service;

import com.skoo.stock.pub.domain.Category;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class CategoryService extends BaseService<Category> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Category> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Category.class);
		String upId = condition.get("upId");
		if (!StringUtils.isEmpty(upId))
			c.add(Restrictions.eq("upId", upId));
		return super.queryPaged(condition);
	}

	/**
	 * 栏目信息取得
	 *
	 * @param map
	 * @return
	 */
	public Category queryCategory(Map map) {
		Category category = this.getSqlSession().selectOne("Category_custlist", map);
		return category;
	}

}
