package cn.hzskt.bdtg.job.mapper;

import cn.hzskt.bdtg.job.domain.Item;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.sys.domain.Dict;
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
public interface ItemMapper extends Mapper<Item>{
    @Select("SELECT count(*) FROM job_item  WHERE valid ='1' AND task=#{param1} AND name = #{param2} AND id != #{param3}")
    public int getcount(Long tid,String name,String id);
}
