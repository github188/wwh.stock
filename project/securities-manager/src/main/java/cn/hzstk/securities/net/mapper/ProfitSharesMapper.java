package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.ProfitShares;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface ProfitSharesMapper extends Mapper<ProfitShares>{
    @Select("select max(dt) from net_profit_shares where valid = '1'")
    String selectMaxDt();
}
