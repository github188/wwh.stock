package com.skoo.stock.pub.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.pub.domain.Channel;
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
public class ChannelService extends BaseService<Channel> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Channel> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Channel.class);
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
	public Channel queryChannel(Map map) {
		Channel channel = this.getSqlSession().selectOne("Channel_custlist", map);
		return channel;
	}

}
