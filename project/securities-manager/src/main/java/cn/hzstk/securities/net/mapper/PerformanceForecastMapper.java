package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.PerformanceForecast;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface PerformanceForecastMapper extends Mapper<PerformanceForecast>{
    @Select("select max(report_dt) from net_performance_forecast where valid = '1'")
    String selectMaxReportDt();
}
