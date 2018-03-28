package cn.hzskt.hsa.service;

import cn.hzskt.hsa.domain.IndustryData;
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
public class  IndustryDataService extends BaseService<IndustryData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<IndustryData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(IndustryData.class);
		String industryType = condition.get("industryType");
		if (StringUtils.isNotEmpty(industryType))
			c.add(Restrictions.like("industryId", industryType + "%"));
		String code = condition.get("industryId");
		if (StringUtils.isNotEmpty(code))
			c.add(Restrictions.eq("industryId", code));
		return super.queryPaged(condition);
	}

	public List<IndustryData> getIndustryInfo(String industryId) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(IndustryData.class);
		c.add(Restrictions.eq("industryId", industryId));

		return super.query(condition);
	}

	public Long saveOrUpdate(IndustryData o) {
		Assert.notNull(o);
		List<IndustryData> list = getIndustryInfo(o.getIndustryId());
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}
}
