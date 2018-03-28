package cn.hzskt.hsa.service;

import cn.hzskt.hsa.domain.HistoryData;
import cn.hzskt.hsa.domain.HistoryDetail;
import com.zjhcsoft.smartcity.magic.orm.service.BaseService;
import com.zjhcsoft.smartcity.magic.orm.service.support.paging.PageInfo;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Condition;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Criteria;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  HistoryDataService extends BaseService<HistoryData> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<HistoryData> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(HistoryData.class);
		String code = condition.get("code");
		if (!StringUtils.isEmpty(code))
			c.add(Restrictions.eq("code", code));
		return super.queryPaged(condition);
	}

	public List<HistoryData> getHistoryInfo(String code) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", code));

		return super.query(condition);
	}

	public List<HistoryData> getHistoryDt(String code,String dt) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", code));
		c.add(Restrictions.between("dt", "2014-08-23", dt));

		return super.query(condition);
	}

	public List<HistoryData> getHistoryDetinfo(String code) {
		HashMap map = new HashMap();
		map.put("code", code);
		map.put("month", -2);

		return super.query("HistoryData_get_by_group", map);
	}

	public List<HistoryData> getHistoryDetinfo1(String code) {
		HashMap map = new HashMap();
		map.put("code", code);
		map.put("month", -6);

		return super.query("HistoryData_get_by_group1", map);
	}

	public Long saveOrUpdate(HistoryData o) {
		Assert.notNull(o);
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(HistoryData.class);
		c.add(Restrictions.eq("code", o.getCode()));
		c.add(Restrictions.eq("dt", o.getDt()));
		List<HistoryData> list = super.query(condition);
		if(list != null && list.size() > 0) {
			//o.setId(list.get(0).getId());
			return null;
		}

		return super.saveOrUpdate(o);
	}
}
