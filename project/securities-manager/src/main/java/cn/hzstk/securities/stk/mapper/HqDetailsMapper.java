package cn.hzstk.securities.stk.mapper;

import cn.hzstk.securities.stk.domain.HqDetails;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface HqDetailsMapper extends Mapper<HqDetails>{
    @Select("select distinct stock_code from stk_hq_details where stock_name = #{value}")
    List<String> getStockCode(String name);
    @Select("select min(dt) from stk_hq_details where valid = '1'")
    String selectMinDt();
}
