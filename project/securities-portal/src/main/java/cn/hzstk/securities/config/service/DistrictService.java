package cn.hzstk.securities.config.service;

import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import cn.hzstk.securities.config.domain.District;
import cn.hzstk.securities.config.mapper.DistrictMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  DistrictService extends BaseService<District,DistrictMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<District> query(Map<String, String> paramMap) {
        Example example = new Example(District.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String upid = paramMap.get("upid");
        if (!StringUtils.isEmpty(upid)) {
            criteria.andLike("upid", "%" + upid+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    public List<District> selectChildren(String pid){
    	Example example = new Example(District.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("upid", pid);
    	return mapper.selectByExample(example);
    }
    
}
