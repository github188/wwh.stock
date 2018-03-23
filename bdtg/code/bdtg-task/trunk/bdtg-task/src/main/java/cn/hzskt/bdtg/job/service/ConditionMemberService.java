package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.ConditionMember;
import cn.hzskt.bdtg.job.mapper.ConditionMemberMapper;
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
public class  ConditionMemberService extends BaseService<ConditionMember,ConditionMemberMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<ConditionMember> query(Map<String, String> paramMap) {
        Example example = new Example(ConditionMember.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String jmbId = paramMap.get("jmbId");
        if (!StringUtils.isEmpty(jmbId)) {
            criteria.andLike("jmbId", "%" + jmbId+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据条件id，用户id查询
     * @param paramMap
     * @return
     */
     public int querybycondition(Long conditionid,Long userid) {
    	 ConditionMember cm = new ConditionMember();
    	 cm.setConditionId(conditionid);
    	 cm.setJmbId(userid);
         return mapper.selectCount(cm);
     }
     
     /**
      * 根据条件id
      * @param paramMap
      * @return
      */
      public int querybycondition(Long conditionid) {
     	 ConditionMember cm = new ConditionMember();
     	  cm.setConditionId(conditionid);
          cm.setValid(1);
          return mapper.selectCount(cm);
      }
}
