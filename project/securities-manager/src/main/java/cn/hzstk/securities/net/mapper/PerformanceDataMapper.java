package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.PerformanceData;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface PerformanceDataMapper extends Mapper<PerformanceData>{
    @Select("select max(report_dt) from net_performance_data where valid = '1'")
    String selectMaxReportDt();
}
