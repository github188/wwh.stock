package cn.hzstk.securities.stockeast.mapper;

import cn.hzstk.securities.stockeast.domain.StockGroup;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface StockGroupMapper extends Mapper<StockGroup>{
    @Select("select max(group_code) from net_stock_group")
    String selectMaxCode();
}
