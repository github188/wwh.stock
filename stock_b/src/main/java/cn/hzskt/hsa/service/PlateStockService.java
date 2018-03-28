package cn.hzskt.hsa.service;

import cn.hzskt.hsa.domain.PlateStock;
import com.zjhcsoft.smartcity.magic.orm.service.BaseService;
import com.zjhcsoft.smartcity.magic.orm.service.support.paging.PageInfo;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Condition;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Criteria;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Restrictions;
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
public class  PlateStockService extends BaseService<PlateStock> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<PlateStock> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(PlateStock.class);
		String conceptId = condition.get("conceptId");
		if (StringUtils.isNotEmpty(conceptId))
			c.add(
					Restrictions.like("conceptId", "%" + conceptId
							+ "%"));
		return super.queryPaged(condition);
	}

	public List<PlateStock> getPlateInfo(String conceptId) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(PlateStock.class);
		if (StringUtils.isNotEmpty(conceptId)) c.add(Restrictions.eq("conceptId", conceptId));

		return super.query(condition);
	}

	public Long saveOrUpdate(PlateStock o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(PlateStock.class);
		c.add(Restrictions.eq("conceptId", o.getConceptId()));
		c.add(Restrictions.eq("code", o.getCode()));
		List<PlateStock> list = super.query(condition);
		if(list != null && list.size() > 0) {
			//o.setId(list.get(0).getId());
			return null;
		}

		return super.saveOrUpdate(o);
	}
}
