package cn.hzskt.bdtg.member.mapper;

import cn.hzskt.bdtg.member.domain.Msg;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface MsgMapper extends Mapper<Msg>{
    @Select("select count(*) from member_msg where valid='1' and view_status='1' and to_uid=#{param1} and type=#{param2}")
    public  int getmsgsize(Long touid,String type);
}
