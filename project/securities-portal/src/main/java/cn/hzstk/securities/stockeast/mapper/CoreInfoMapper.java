package cn.hzstk.securities.stockeast.mapper;

import cn.hzstk.securities.stockeast.domain.CoreInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface CoreInfoMapper extends Mapper<CoreInfo>{
    @Select("select max(dt) from net_core_info where valid = '1' and stock_code = #{value}")
    String selectMaxDt(String code);
}
