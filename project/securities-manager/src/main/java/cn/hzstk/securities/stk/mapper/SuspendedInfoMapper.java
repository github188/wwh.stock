package cn.hzstk.securities.stk.mapper;

import cn.hzstk.securities.stk.domain.SuspendedInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface SuspendedInfoMapper extends Mapper<SuspendedInfo>{
    @Select("select max(start_date) from stk_suspended_info where valid = '1'")
    String selectMaxStartDt();
    @Update("update stk_suspended_info set update_date = current_date,valid = '0' where valid = '1' and stock_code > #{stockCode}")
    void updateSuspendedInfo(SuspendedInfo o);
}
