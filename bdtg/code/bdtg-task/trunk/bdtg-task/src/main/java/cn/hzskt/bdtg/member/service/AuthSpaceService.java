package cn.hzskt.bdtg.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.DataSourceContextHolder;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  AuthSpaceService extends BaseService<AuthSpace,AuthSpaceMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthSpace> query(Map<String, String> paramMap) {
        DataSourceContextHolder.setCustomerType("manager_ds");
        Example example = new Example(AuthSpace.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid", "1");
        
        String jobsearch = MapUtils.getString(paramMap, "jobsearch", "");
        if("1".equals(jobsearch)){
        	//详见BudgetAction.memberPaged
            String userName = MapUtils.getString(paramMap, "userName");
            if (!StringUtils.isEmpty(userName)) {
            	criteria.andLike("userName", "%" + userName + "%");
            }
            List ids = (List) MapUtils.getObject(paramMap, "ids", new ArrayList());
            if(ids!=null && ids.size() > 0){
            	criteria.andIn("userId", ids);
            }
            return mapper.selectByExample(example);
        }
        
        String userName = paramMap.get("userName");
        List<AuthSpace> list =new ArrayList<AuthSpace>();
        if (!StringUtils.isEmpty(userName)) {
            criteria.andLike("userName", "%" + userName+ "%");
            list=  mapper.selectByExample(example);
        }else{
            list=null;
        }
        DataSourceContextHolder.clearCustomerType();
        return list;
    }
    
    public AuthSpace getAuthSpaceByUsrId(String usrId){
    	DataSourceContextHolder.setCustomerType("manager_ds");
    	Example example = new Example(AuthSpace.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("userId", usrId);
        List<AuthSpace> items = this.mapper.selectByExample(example);
        DataSourceContextHolder.clearCustomerType();
    	return (items!=null && items.size() > 0)?items.get(0):null;
    }
    
}
