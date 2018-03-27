package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.domain.Dict;
import cn.hzstk.securities.sys.mapper.DictMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
         example.setOrderByClause("order_by ASC");
         return mapper.selectByExample(example);
    }
    
    public Dict selectDict(String keyname, String val){
    	if(StringUtils.isBlank(keyname) || StringUtils.isBlank(val)){
    		return null;
    	}
    	Example example = new Example(Dict.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("keyName", keyname);
    	criteria.andEqualTo("value", val);
    	criteria.andEqualTo("valid", 1);
    	List<Dict> dicts = mapper.selectByExample(example);
    	return (dicts!=null && dicts.size() > 0) ? dicts.get(0):null;
    }

    public String selectDictNameByArray(String keyname, String val, String split, String append){
    	StringBuffer buff = new StringBuffer();
    	if(StringUtils.isNotBlank(val)){
    		String[] array = val.split(split);
    		List<Dict> dicts = this.selectDictItems(keyname, array);
    		for(int i=0; i < dicts.size(); i++){
    			Dict item = dicts.get(i);
    			buff.append(item.getContent());
    			if(i < dicts.size() - 1){
    				buff.append(append);
    			}
    		}
    	}
    	return buff.toString();
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
    
    /**
     * 查询字典信息按照排序号进行排序，并对值进行过滤
     * @param keyName
     * @param vals
     * @return
     */
    public List<Dict> selectDictItemsWithOut(String keyName, Object[] vals){
    	Example example = new Example(Dict.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("keyName", keyName);
    	criteria.andEqualTo("valid", 1);
    	if(vals!=null && vals.length > 0){
    		criteria.andNotIn("value", Arrays.asList(vals));
    	}
    	example.setOrderByClause("order_by asc");
    	List<Dict> items = mapper.selectByExample(example);
    	return items;
    }

}
