package com.skoo.stock.hsa.service;

import com.skoo.commons.DateUtils;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.hsa.domain.IndexData;
import com.skoo.stock.util.StockUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  IndexDataService extends BaseService<IndexData> {
	private static final Logger LOG = LoggerFactory.getLogger("mytest");

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<IndexData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(IndexData.class);
		String stocktype = condition.get("stocktype");
		if (StringUtils.isNotEmpty(stocktype)) {
			if ("4".equals(stocktype)) {
				c.add(Restrictions.eq("code", "shb"));
			} else if ("6".equals(stocktype)) {
				c.add(Restrictions.eq("code", "sha"));
			} else if ("3".equals(stocktype)) {
				c.add(Restrictions.eq("code", "gem"));
			} else if ("0".equals(stocktype)) {
				c.add(Restrictions.eq("code", "small"));
			}
		}
		String code = condition.get("code");
		if (StringUtils.isNotEmpty(code))
			c.add(Restrictions.like("code", "%" + code + "%"));
		String dt = condition.get("dt");
		if (StringUtils.isNotEmpty(dt)) {
			c.add(Restrictions.eq("dt", dt));
		} else {
			if (StringUtils.isEmpty(stocktype) && StringUtils.isEmpty(code))
				c.add(Restrictions.eq("dt", DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR)));
		}
		return super.queryPaged(condition);
	}

	public Long saveOrUpdate(IndexData o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(IndexData.class);
		c.add(Restrictions.eq("code", o.getCode()));
		c.add(Restrictions.eq("dt", o.getDt()));
		List<IndexData> list = super.query(condition);
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
			o.setCurUdWidth(StockUtil.calcWidth(o.getClosingPrice(), list.get(0).getClosingPrice(), o.getEndPrice()));
			LOG.info(o.getCode() + "|" + o.getClosingPrice() + "|" + o.getUdWidth() + "|" + o.getUdAmount() + "|" + o.getTurnVolume()
					+ "|" + o.getCurUdWidth());
			if (StringUtils.isNotEmpty(o.getRiseCnt())) LOG.info(o.getRiseCnt() + "|" + o.getFlatCnt() + "|" + o.getFallCnt());
		}

		return super.saveOrUpdate(o);
	}
}
