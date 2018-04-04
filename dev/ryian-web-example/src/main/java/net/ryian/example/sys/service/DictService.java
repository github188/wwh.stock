package net.ryian.example.sys.service;

import net.ryian.commons.StringUtils;
import net.ryian.example.sys.domain.Dict;
import net.ryian.example.sys.mapper.DictMapper;
import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.annotation.DataSource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allenwc on 15/9/8.
 */
@Component
@DataSource("ds2")
public class DictService extends BaseService<Dict,DictMapper> {


    @Override
    public List<Dict> query(Map<String,String> paramMap) {
        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        String keyName = paramMap.get("keyName");
        if (!StringUtils.isEmpty(keyName)) {
            criteria.andLike("keyName","%"+keyName+"%");
        }
        String value = paramMap.get("value");
        if(!StringUtils.isEmpty(value)) {
            criteria.andLike("value","%"+value+"%");
        }
        return mapper.selectByExample(example);
    }

    /**
     * 根据数据字典类型获取相应数据字典项
     * @param key
     * @return
     */
    @Cacheable(value="sys",key="#key")
    public Map<String ,Dict> getDictsByKey(String key){
        Map<String ,Dict> dicMap = new TreeMap<String,Dict>();
        Dict d = new Dict();
        d.setKeyName(key);
        List<Dict> dicts = mapper.select(d);
        for(Dict dict : dicts) {
            dicMap.put(dict.getValue(), dict);
        }
        return dicMap;
    }

    public List<String> getKeyNames() {
        return mapper.getKeyNames();
    }

}
