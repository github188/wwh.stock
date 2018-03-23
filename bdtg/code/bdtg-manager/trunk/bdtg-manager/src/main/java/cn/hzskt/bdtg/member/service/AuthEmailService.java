package cn.hzskt.bdtg.member.service;

import cn.hzskt.bdtg.member.domain.AuthEmail;
import cn.hzskt.bdtg.member.mapper.AuthEmailMapper;
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
public class  AuthEmailService extends BaseService<AuthEmail,AuthEmailMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthEmail> query(Map<String, String> paramMap) {
        Example example = new Example(AuthEmail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String email = paramMap.get("email");
        if (!StringUtils.isEmpty(email)) {
            criteria.andLike("email", "%" + email+ "%");
        }
return mapper.selectByExample(example);
    }
}
