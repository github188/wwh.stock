package cn.hzstk.securities.stk.mapper;

import cn.hzstk.securities.stk.domain.RxData;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface RxDataMapper extends Mapper<RxData>{
    @Select("select code stock_code from stk_stocks_info where stype = 'a' and status != 2 and valid='1' and code >= #{value}")
    List<String> selectStockInfo(String code);
    @Select("select distinct stock_code from stk_hq_details where valid = '1'")
    List<String> getAllStockCode();
}
