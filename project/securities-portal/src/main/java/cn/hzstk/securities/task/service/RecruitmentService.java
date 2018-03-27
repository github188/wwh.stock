package cn.hzstk.securities.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import cn.hzstk.securities.task.domain.Recruitment;
import cn.hzstk.securities.task.mapper.RecruitmentMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  RecruitmentService extends BaseService<Recruitment,RecruitmentMapper> {

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
        return mapper.selectByExample(example);
    }
    
    public PageInfo selectPaged(Map<String, String> paramMap){
    	int pageNum = MapUtils.getInteger(paramMap, "page", 1);
    	if(pageNum < 1) pageNum = 1;
    	int pagesize = MapUtils.getInteger(paramMap, "size", 15);
    	if(pagesize < 1) pagesize = 15;
    	this.selectPageOrderBy(paramMap);
    	PageHelper.startPage(pageNum, pagesize);
    	List items = this.sqlSession.selectList("Recruitment_select_all", paramMap);
    	return new PageInfo(items);
    }
    
    private void selectPageOrderBy(Map<String, String> paramMap){
    	String orderByStr = "", ascStr = "";
    	
    	String orderBy = MapUtils.getString(paramMap, "orderBy", "1");	//1、发布时间 	2、截止时间
    	if("1".equals(orderBy)){
    		orderByStr = "create_date";
    	}
    	if("2".equals(orderBy)){
    		orderByStr = "subtime";
    	}
    	
    	
    	String asc = MapUtils.getString(paramMap, "asc", "0");
    	if("0".equals(asc)){
    		ascStr = "desc";
    	}
    	if("1".equals(asc)){
    		ascStr = "asc";
    	}
    	paramMap.put("orderByStr", orderByStr);
    	paramMap.put("orderBy", orderBy);
    	paramMap.put("ascStr", ascStr);
    	paramMap.put("asc", asc);
    }
    
    public List selectFinished(int num) {
    	Map param = new HashMap();
    	param.put("status", "2");
    	param.put("orderByStr", "update_date");
    	param.put("ascStr", "desc");
    	
    	PageHelper.startPage(1, num);
    	
    	return this.sqlSession.selectList("Recruitment_select_all", param);
    }
    
}
