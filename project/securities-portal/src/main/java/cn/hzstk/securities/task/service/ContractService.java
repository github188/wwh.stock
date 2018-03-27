package cn.hzstk.securities.task.service;

import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import cn.hzstk.securities.task.domain.Contract;
import cn.hzstk.securities.task.mapper.ContractMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  ContractService extends BaseService<Contract,ContractMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Contract> query(Map<String, String> paramMap) {
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tkId = paramMap.get("tkId");
        if (!StringUtils.isEmpty(tkId)) {
        	criteria.andEqualTo("tkId", tkId);
        }
        example.setOrderByClause("ifnull(py_status, 0) desc, py_time asc");
        return mapper.selectByExample(example);
    }
    
    
    public void addTskContract(List<Contract> data){
    	if(data == null || data.size() < 1) return;
    	for(Contract model : data){
    		this.saveOrUpdate(model);
    	}
    }
    
    public List<Contract> selectItems(String tkId){
    	Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("tkId", tkId);
        example.setOrderByClause("py_time asc");
        return mapper.selectByExample(example);
    }
    
}
