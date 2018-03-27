package cn.hzstk.securities.east.mapper;

import cn.hzstk.securities.east.domain.ProfitReport;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface ProfitReportMapper extends Mapper<ProfitReport>{
    @Select("select max(dt) from net_profit_report where valid = '1' and stock_code = #{value}")
    String selectMaxDt(String code);
}
