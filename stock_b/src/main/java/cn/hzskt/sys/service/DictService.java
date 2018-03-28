package cn.hzskt.sys.service;

import cn.hzskt.sys.domain.Dict;
import com.zjhcsoft.smartcity.magic.commons.StringUtils;
import com.zjhcsoft.smartcity.magic.orm.service.BaseService;
import com.zjhcsoft.smartcity.magic.orm.service.support.paging.PageInfo;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Condition;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Criteria;
import com.zjhcsoft.smartcity.magic.orm.service.support.query.Restrictions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DictService extends BaseService<Dict>{

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Dict> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Dict.class);
		String keyName = condition.get("keyName");
		if (!StringUtils.isEmpty(keyName))
			c.add(
					Restrictions.like("keyName", "%" + keyName
							+ "%"));
		String value = condition.get("value");
		if(!StringUtils.isEmpty(value)) {
			c.add(Restrictions.eq("value", value));
		}
		return super.queryPaged(condition);
	}

	/**
	 * 根据数据字典类型获取相应数据字典项
	 * @param key
	 * @return
	 */
	@Cacheable(value="sys",key="#key")
	public Map<String ,Dict> getDictsByKey(String key){
		Map<String ,Dict> dicMap = new LinkedHashMap<String, Dict>();
		Condition condition= new Condition();
		Criteria c = condition.createCriteria(Dict.class);
		c.add(Restrictions.eq("keyName", key));
		List<Dict> dicts = this.query(condition);
		for(Dict dict : dicts) {
			dicMap.put(dict.getValue(), dict);
		}
		return dicMap;
	}

	public List<Dict> getDictInfo(String keyName, String value) {
		Condition condition = new Condition();
		Criteria c = condition.createCriteria(Dict.class);
		c.add(Restrictions.eq("keyName", keyName));
		c.add(Restrictions.eq("value", value));

		return super.query(condition);
	}

	public Long saveOrUpdate(Dict o) {
		Assert.notNull(o);
		List<Dict> list = getDictInfo(o.getKeyName(), o.getValue());
		if(list != null && list.size() > 0) {
			o.setId(list.get(0).getId());
		}

		return super.saveOrUpdate(o);
	}

	public List<String> getKeyNames() {
		return this.getSqlSession().selectList("Dict_getKeyNames");
	}


}
