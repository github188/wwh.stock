package cn.hzskt.bdtg.member.mapper;

import cn.hzskt.bdtg.member.domain.AuthRealname;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface AuthRealnameMapper extends Mapper<AuthRealname>{
    @Select("select id,auth_status as authStatus from member_auth_realname where valid='1' and user_id=#{value}")
    public List<AuthRealname> getlist(Long userId);
}
