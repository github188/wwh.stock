package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.AuthBank;
import cn.hzstk.securities.financial.mapper.AuthBankMapper;
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
public class  AuthBankService extends BaseService<AuthBank,AuthBankMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthBank> query(Map<String, String> paramMap) {
        Example example = new Example(AuthBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String id = paramMap.get("id");
        String username = paramMap.get("username");
        String authStatus = paramMap.get("authstatus");
        if (!StringUtils.isEmpty(username)) {
            criteria.andLike("userName", "%" + username + "%");
        }
        if (!StringUtils.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
        if (!StringUtils.isEmpty(authStatus)&& !authStatus.equals("-1")) {
            criteria.andEqualTo("authStatus", authStatus);
        }
return mapper.selectByExample(example);
    }
}
