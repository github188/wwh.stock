package cn.hzstk.securities.config.service;

import cn.hzstk.securities.config.domain.Industry;
import cn.hzstk.securities.config.mapper.IndustryMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

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
        String indusId = paramMap.get("indusId");
        if (!StringUtils.isEmpty(indusId)) {
            criteria.andLike("indusId", "%" + indusId+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据pid查询
     * @param pid
     * @return
     */
    public List<Industry> getIndustrysByPid(Integer pid) {
		Industry industry = new Industry();
		industry.setIndusPid(pid);
		return mapper.select(industry);
	}
    
    /**
     * 查询二级目录
     * @param pid
     * @return
     */
    public List<Industry> getsecondary() {
		Industry industry = new Industry();
		industry.setIndusPid(0);
		return mapper.select(industry);
	}
    
}
