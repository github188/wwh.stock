package cn.hzskt.bdtg.task.service;

import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import cn.hzskt.bdtg.task.domain.Recruitment;
import cn.hzskt.bdtg.task.mapper.RecruitmentMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  RecruitmentService extends BaseService<Recruitment, RecruitmentMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Recruitment> query(Map<String, String> paramMap) {
        Example example = new Example(Recruitment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String zwmc = paramMap.get("zwmc");
        if (!StringUtils.isEmpty(zwmc)) {
            criteria.andLike("zwmc", "%" + zwmc+ "%");
        }
        zwmc = paramMap.get("status");
        if (!StringUtils.isEmpty(zwmc)) {
            criteria.andEqualTo("status", zwmc);
        }
        return mapper.selectByExample(example);
    }
    
    public void passZmrc(String id){
    	changeStatus(id, "1");
    }
    
    public void rejectZmrc(String id){
    	changeStatus(id, "4");
    }
    
    private void changeStatus(String id, String status){
    	if(StringUtils.isNotBlank(id)){
    		for(String s : id.split(",")){
    			Recruitment a = new Recruitment();
    			a.setId(Long.parseLong(s));
    			a.setStatus(status);
    			this.saveOrUpdate(a);
    		}
    	}
    }
    
}
