package cn.hzstk.securities.member.mapper;

import cn.hzstk.securities.member.domain.AuthCompany;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface AuthCompanyMapper extends Mapper<AuthCompany>{
    @Select("select id,auth_status as authStatus from member_auth_company where valid='1' and user_id=#{value}")
    public List<AuthCompany> getlist(Long userId);
}
