package cn.hzskt.bdtg.config.mapper;

import cn.hzskt.bdtg.config.domain.District;
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
public interface DistrictMapper extends Mapper<District>{
    @Select("select a1.*,a2.name as upname from config_district a1,config_district a2 where a1.valid='1' and a1.type=2 and a2.id=a1.upid")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "upid", column = "upid"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "displayorder", column = "displayorder"),
            @Result(property = "upname", column = "upname")
    })
    public List<?> getTwoByUpid(String upid);

    @Select("select * from config_district where valid='1' and type=2")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "upid", column = "upid"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "displayorder", column = "displayorder"),
    })
    public List<District> getTwoByType();

    @Select("select a1.*,a2.name as upname from config_district a1,config_district a2 where a1.valid='1' and a1.type=3 and a2.id=a1.upid")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "upid", column = "upid"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "displayorder", column = "displayorder")
    })
    public List<District> getThreeByType();

    @Select("select name from config_district where valid='1' and id= #{value}")
    public List<String> getUp(String upid);

    @Select("select name from config_district where valid='1' and id in(select upid from config_district where valid='1' and id= #{value})")
    public List<String> getTop(String upid);

    @Select("select id from config_district where valid='1' and id in(select upid from config_district where valid='1' and id= #{value})")
    public List<String> getTopId(String upid);

}
