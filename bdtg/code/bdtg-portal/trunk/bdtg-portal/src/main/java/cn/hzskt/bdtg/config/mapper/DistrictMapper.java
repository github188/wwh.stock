package cn.hzskt.bdtg.config.mapper;

import cn.hzskt.bdtg.config.domain.District;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface DistrictMapper extends Mapper<District>{
    @Select("select count(*) from config_district where valid='1' and upid=#{value}")
    public int getcount(Long upid);
}
