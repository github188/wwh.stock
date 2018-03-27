package com.skoo.stock.hsa.service;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.common.Constant;
import com.skoo.stock.hsa.domain.MarketData;
import com.skoo.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class  MarketDataService extends BaseService<MarketData> {
	private static final Logger LOG = LoggerFactory.getLogger("mytest");

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<MarketData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(MarketData.class);
		HashMap<String,String> parameterObject = (HashMap<String,String>) condition.getMap();
		parameterObject.put("netFlag", Constant.NETFLG);
		String stocktype = condition.get("stocktype");
		if (StringUtils.isNotEmpty(stocktype)) {
			if ("4".equals(stocktype)) {
				c.add(Restrictions.between("code", "0", "4"));
			} else {
				parameterObject.put("code", stocktype + "%");
			}
		}
		String conceptId = condition.get("conceptId");
		if (StringUtils.isNotEmpty(conceptId)) parameterObject.put("industryType", conceptId);
		String industryId = condition.get("industryId");
		if (StringUtils.isNotEmpty(industryId)) parameterObject.put("conceptId", industryId);
		/*	c.add(Restrictions.eq("conceptId", industryId));
		else if (StringUtils.isNotEmpty(conceptId))
			c.add(Restrictions.eq("conceptId", conceptId));
		String code = condition.get("code");
		if (StringUtils.isNotEmpty(code))
			c.add(Restrictions.eq("code", code));*/
		return super.queryPaged("MarketData_platelist",condition);
		//return super.queryPaged(condition);
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
		c.add(Restrictions.eq("netFlag", o.getNetFlag()));
		List<MarketData> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
			if (StringUtils.isEmpty(o.getTurnoverRate())) {
				o.setFiveWidth(String.format("%.2f", StockUtil.calcWidth(o.getLatestPrice(), list.get(0).getLatestPrice(), StockUtil.cvtDouble(o.getFiveWidth()))));
				LOG.info(o.getCode()+"|"+o.getLatestPrice()+"|"+o.getUdWidth()+"|"+o.getUdAmount()+"|"+o.getTurnVolume()+"|"+o.getFiveWidth());
			}
		}

		return super.saveOrUpdate(o);
	}
}
