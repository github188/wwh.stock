package cn.hzskt.bdtg.sys.service;

import cn.hzskt.bdtg.sys.mapper.DictMapper;
import cn.hzskt.bdtg.sys.domain.Dict;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allenwc on 15/9/8.
 */
@Component
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
    
    public List<Dict> selectDictByKeyName(String keyname){
    	 Example example = new Example(Dict.class);
         Example.Criteria criteria = example.createCriteria();
         criteria.andEqualTo("keyName", keyname);
         criteria.andEqualTo("valid", 1);
         return mapper.selectByExample(example);
    }
    
    public Dict selectDict(String keyname, String val){
    	Example example = new Example(Dict.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("keyName", keyname);
    	criteria.andEqualTo("value", val);
    	criteria.andEqualTo("valid", 1);
    	List<Dict> dicts = mapper.selectByExample(example);
    	return (dicts!=null && dicts.size() > 0) ? dicts.get(0):null;
    }

    /**
     * 根据数据字典类型获取相应数据字典项
     * @param key
     * @return
     */
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
    
    public List<Dict> selectDictItems(String keyName,  Object[] vals){
    	if(vals == null || vals.length < 1) return new ArrayList<Dict>(); 
    	Example example = new Example(Dict.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("keyName", keyName);
    	criteria.andEqualTo("valid", 1);
    	criteria.andIn("value", Arrays.asList(vals));
    	return mapper.selectByExample(example);
    }

    public List<String> getKeyNames() {
        return mapper.getKeyNames();
    }

}
