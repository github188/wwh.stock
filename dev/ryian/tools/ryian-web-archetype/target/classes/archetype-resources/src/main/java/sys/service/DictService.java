#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.service;

        import net.ryian.commons.StringUtils;
        import net.ryian.core.service.BaseService;
        import net.ryian.core.service.support.paging.PageInfo;
        import net.ryian.core.service.support.query.Condition;
        import net.ryian.core.service.support.query.Criteria;
        import net.ryian.core.service.support.query.Restrictions;
        import ${package}.sys.domain.Dict;
        import org.springframework.cache.annotation.Cacheable;
        import org.springframework.stereotype.Component;
        import org.springframework.util.Assert;

        import java.util.List;
        import java.util.Map;
        import java.util.TreeMap;

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
        if (!StringUtils.isEmpty(keyName))
            c.add(
                    Restrictions.like("keyName", "%" + keyName
                            + "%"));
        String value = condition.get("value");
        if (!StringUtils.isEmpty(value)) {
            c.add(Restrictions.eq("value", value));
        }
        return super.queryPaged(condition);
    }

    /**
     * 根据数据字典类型获取相应数据字典项
     *
     * @param key
     * @return
     */
    @Cacheable(value = "sys", key = "${symbol_pound}key")
    public Map<String, Dict> getDictsByKey(String key) {
        Map<String, Dict> dicMap = new TreeMap<String, Dict>();
        Condition condition = new Condition();
        Criteria c = condition.createCriteria(Dict.class);
        c.add(Restrictions.eq("keyName", key));
        List<Dict> dicts = this.query(condition);
        for (Dict dict : dicts) {
            dicMap.put(dict.getValue(), dict);
        }
        return dicMap;
    }


    public List<String> getKeyNames() {
        return this.getSqlSession().selectList("Dict_getKeyNames");
    }


}
