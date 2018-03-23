package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Condition;
import cn.hzskt.bdtg.job.mapper.ConditionMapper;
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
public class  ConditionService extends BaseService<Condition,ConditionMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Condition> query(Map<String, String> paramMap) {
        Example example = new Example(Condition.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
//        criteria.
        String tid = paramMap.get("tid");
        if (!StringUtils.isEmpty(tid)) {
            criteria.andLike("tid", "%" + tid+ "%");
        }
        example.setOrderByClause("major");
        return mapper.selectByExample(example);
    }
    
    /**
     * 查询
     * @param paramMap
     * @return
     */
     public List<Condition> list(Map<String, String> paramMap) {
         Example example = new Example(Condition.class);
         Example.Criteria criteria = example.createCriteria();
         criteria.andEqualTo("valid","1");
         String tid = paramMap.get("tid");
         if (!StringUtils.isEmpty(tid)) {
             criteria.andEqualTo("tid", tid);
         }
         String major = paramMap.get("major");
         if (major!=null) {
             criteria.andEqualTo("major", major);
         }
         example.setOrderByClause("major");
         return mapper.selectByExample(example);
     }
}
