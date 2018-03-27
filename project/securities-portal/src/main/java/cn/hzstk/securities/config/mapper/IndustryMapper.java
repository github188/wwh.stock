package cn.hzstk.securities.config.mapper;

import cn.hzstk.securities.config.domain.Industry;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface IndustryMapper extends Mapper<Industry>{
    @Select("select count(*) from config_industry where valid='1' and indus_pid=#{value}")
    public int getcount(Long upid);
}
