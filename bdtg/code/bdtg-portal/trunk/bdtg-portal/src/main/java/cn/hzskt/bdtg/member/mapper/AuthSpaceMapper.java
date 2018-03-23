package cn.hzskt.bdtg.member.mapper;

import cn.hzskt.bdtg.member.domain.AuthSpace;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface AuthSpaceMapper extends Mapper<AuthSpace>{
    @Select("select id,auth_status as authStatus,user_type as userType,reg_time as regTime,last_login_time as lastLoginTime from member_auth_space where valid='1' and user_id=#{value}")
    public List<AuthSpace> getlist(Long userId);
}
