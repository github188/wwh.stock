package cn.hzskt.bdtg.job.mapper;

import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface MemberMapper extends Mapper<Member>{

    @Select("select count(*) from job_member where valid='1' and user_id=#{userId} and tid = #{tid}")
    public int  getcount(Map map);

    @Select("select count(*) from job_member where valid='1' and type=#{submbtype} and tid = #{tid}")
    public int  getsubcount(Map map);
}
