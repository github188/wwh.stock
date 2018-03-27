package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.mapper.DictMapper;
import cn.hzstk.securities.sys.domain.Dict;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@SuppressWarnings("JavaDoc")
@Component
public class DictService extends BaseService<Dict,DictMapper> {


    @Override
    public List<Dict> query(Map<String,String> paramMap) {
        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
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
    public Map<String ,Dict> getDictsByKey(String key){
        Map<String ,Dict> dicMap = new TreeMap<>();
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
    
    /**
	 * 通过字典查询在线平台信息
	 * @return
	 */
	public List<Dict> selectPlatform(){
		return this.selectDictByKeyName("onLinePlaform");
	}
	
	public List<Dict> selectDictByKeyName(String keyName){
		Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keyName", keyName);
		return mapper.selectByExample(example);
	}

}
