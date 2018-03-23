package cn.hzskt.bdtg.config.service;

import cn.hzskt.bdtg.config.domain.Industry;
import cn.hzskt.bdtg.config.mapper.IndustryMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  IndustryService extends BaseService<Industry,IndustryMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Industry> query(Map<String, String> paramMap) {
        Example example = new Example(Industry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String indusPid = paramMap.get("indusPid");
        criteria.andEqualTo("indusPid", indusPid);
        return mapper.selectByExample(example);
    }
    
    public List<Industry> selectChildren(String pid){
    	Example example = new Example(Industry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("indusPid", pid);
    	return mapper.selectByExample(example);
    }
    
    /**
     * 根据用户类型查询分类信息
     * @param pid 父节点编码
     * @param usrType 1、业主 2、设计院
     * @return
     */
    public List<Industry> selectByUsrType(String pid, String usrType){
    	
    	if(StringUtils.isBlank(usrType)){
    		return new ArrayList<Industry>();
    	}
    	Example example = new Example(Industry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("indusPid", pid);
        
        if("1".equals(usrType)){
        	criteria.andEqualTo("ownflag", 1);
        }
        if("2".equals(usrType)){
        	criteria.andEqualTo("dsgflag", 1);
        }
    	return mapper.selectByExample(example);
    }
    
    public Industry getIndustry(String id){
    	Example example = new Example(Industry.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("valid","1");
    	criteria.andEqualTo("id", id);
    	List<Industry> items = mapper.selectByExample(example);
    	return (items!=null && items.size() > 0) ? items.get(0):null;
    }
    
    /**
     * 查询分类列表，用户展示任务列表里的分类信息
     * @return
     */
    public List<Map<String, Object>> selectAllMap(){
    	List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    	for(Industry data : this.selectChildren("0")){
    		Map<String, Object> item = new HashMap<String, Object>();
    		item.put("model", data);
    		item.put("children", this.selectChildren(String.valueOf(data.getId())));
    		items.add(item);
    	}
    	return items;
    }

}
