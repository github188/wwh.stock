package com.skoo.stock.sys.service;

import com.skoo.commons.StringUtils;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.sys.domain.Dict;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class DictService extends BaseService<Dict> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<Dict> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(Dict.class);
        String keyName = condition.get("keyName");
        if (!StringUtils.isEmpty(keyName)) {
            c.add(Restrictions.like("keyName", "%" + keyName + "%"));
        }
        String value = condition.get("value");
        if (!StringUtils.isEmpty(value)) {
            c.add(Restrictions.eq("value", value));
        }
        return super.queryPaged(condition);
    }

    /**
     * 清除字典数据
     *
     * @return
     */
    @CacheEvict(value = "sys", allEntries = true)
    public void removeAll() {
    }

    /**
     * 根据数据字典类型获取相应数据字典项
     *
     * @param keyName 键值
     * @return
     */
    @Cacheable(value = "sys", key = "#keyName")
    public Map<String, Dict> getDictsByKey(String keyName) {
        Map<String, Dict> dicMap = new LinkedHashMap<String, Dict>();
        Condition condition = new Condition();
        Criteria c = condition.createCriteria(Dict.class);
        c.add(Restrictions.eq("keyName", keyName));
        List<Dict> dicts = this.query(condition);
        for (Dict dict : dicts) {
            dicMap.put(dict.getValue(), dict);
        }
        return dicMap;
    }

    /**
     * 根据数据字典类型获取相应数据字典项
     *
     * @param keyName 键值
     * @return
     */
    public List<Dict> getDictsInfo(String keyName) {
        Condition condition = new Condition();
        Criteria c = condition.createCriteria(Dict.class);
        c.add(Restrictions.eq("keyName", keyName));
        return this.query(condition);
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

    public List<String> getSysParam(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("keyName", key);
        return this.getSqlSession().selectList("Param_getKeyValue", map);
    }

}
