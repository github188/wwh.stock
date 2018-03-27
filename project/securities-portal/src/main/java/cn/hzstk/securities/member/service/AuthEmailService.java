package cn.hzstk.securities.member.service;

import cn.hzstk.securities.member.domain.AuthEmail;
import cn.hzstk.securities.member.mapper.AuthEmailMapper;
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

    /**
     * 根据用户Id和ucode获取邮箱验证表的信息
     * @param userid 用户ID
     * @param ucode 随机编码
     * @return
     */
    public List<AuthEmail> getByuseridAnducode(String userid,String ucode) {
        AuthEmail authEmail = new AuthEmail();
        authEmail.setUserId(Long.parseLong(userid));
            authEmail.setUcode(ucode);
        return mapper.select(authEmail);
    }
    /**
     * 根据用户Id和ucode获取邮箱验证表的信息
     * @param userid 用户ID
     * @return
     */
    public List<AuthEmail> getByuserid(Long userid) {
        AuthEmail authEmail = new AuthEmail();
        authEmail.setUserId(userid);
        return mapper.select(authEmail);
    }
}
