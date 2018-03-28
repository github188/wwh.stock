package cn.hzskt.hsa.service;

import com.zjhcsoft.smartcity.magic.orm.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.zjhcsoft.smartcity.magic.orm.service.BaseService;
import com.zjhcsoft.smartcity.magic.orm.service.support.paging.PageInfo;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Condition;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Criteria;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Restrictions;
import cn.hzskt.hsa.domain.MarketData;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  MarketDataService extends BaseService<MarketData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<MarketData> queryPaged(Condition condition) {
		Assert.notNull(condition);
/*
		Criteria c = condition.createCriteria(MarketData.class);
		String conceptId = condition.get("conceptId");
		if (StringUtils.isNotEmpty(conceptId))
			c.add(Restrictions.eq("conceptId", conceptId));
		String code = condition.get("code");
		if (!StringUtils.isEmpty(code))
			c.add(Restrictions.eq("code", code));
*/
		return super.queryPaged("MarketData_platelist",condition);
	}

	public List<MarketData> getStockInfo() {
		Condition condition = new Condition();

		return super.query(condition);
	}

	public Long saveOrUpdate(MarketData o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(MarketData.class);
		c.add(Restrictions.eq("code", o.getCode()));
		List<MarketData> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}
}
