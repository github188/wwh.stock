package cn.hzskt.bdtg.member.service;

import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;
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
public class  AuthSpaceService extends BaseService<AuthSpace,AuthSpaceMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthSpace> query(Map<String, String> paramMap) {
        Example example = new Example(AuthSpace.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String userName = paramMap.get("userName");
        if (!StringUtils.isEmpty(userName)) {
            criteria.andLike("userName", "%" + userName+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据用户id查询
     * @return
     */
     public AuthSpace getbyuid(Integer userId) {
    	 AuthSpace as = new AuthSpace();
    	 as.setUserId(userId.intValue());
    	 as.setValid(1);
         return mapper.selectOne(as);
     }
}
