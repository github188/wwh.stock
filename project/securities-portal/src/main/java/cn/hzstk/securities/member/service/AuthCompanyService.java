package cn.hzstk.securities.member.service;

import cn.hzstk.securities.member.domain.AuthCompany;
import cn.hzstk.securities.member.mapper.AuthCompanyMapper;
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
public class  AuthCompanyService extends BaseService<AuthCompany,AuthCompanyMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthCompany> query(Map<String, String> paramMap) {
        Example example = new Example(AuthCompany.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
return mapper.selectByExample(example);
    }
}
