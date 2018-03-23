package cn.hzskt.bdtg.member.service;

import cn.hzskt.bdtg.member.domain.AuthRealname;
import cn.hzskt.bdtg.member.mapper.AuthRealnameMapper;
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
public class  AuthRealnameService extends BaseService<AuthRealname,AuthRealnameMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthRealname> query(Map<String, String> paramMap) {
        Example example = new Example(AuthRealname.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        String userName = paramMap.get("userName");
        String authStatus = paramMap.get("authstatus");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        if (!StringUtils.isEmpty(userName)) {
            criteria.andLike("userName", "%" + userName+ "%");
        }
        if (!StringUtils.isEmpty(authStatus)&& !authStatus.equals("-1")) {
            criteria.andEqualTo("authStatus", authStatus);
        }
        example.setOrderByClause("createDate desc");
        return mapper.selectByExample(example);
    }
}
