package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Budget;
import cn.hzskt.bdtg.job.mapper.BudgetMapper;
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
public class  BudgetService extends BaseService<Budget,BudgetMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Budget> query(Map<String, String> paramMap) {
        Example example = new Example(Budget.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String processId = paramMap.get("processId");
        if (!StringUtils.isEmpty(processId)) {
            criteria.andLike("processId", "%" + processId+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    public Map selectMemberPaged(Map params, int page, int rows){
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put("rows", new ArrayList());
    	data.put("total", 100);
    	return data;
    }
    
    public List<String> queryList(String processId){
    	Example example = new Example(Budget.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("processId", Long.parseLong(processId));
        List<String> items = new ArrayList<String>();
        for(Budget bean : mapper.selectByExample(example)){
        	items.add(String.valueOf(bean.getUid()));
        }
        return items;
    }
    
    public double sumUsrJobFare(String taskId, String usrId){
    	return this.mapper.getUsrJobFare(taskId, usrId);
    }
    
}
