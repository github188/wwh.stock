package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.StockGroup;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface StockGroupMapper extends Mapper<StockGroup>{
    @Select("select max(group_code) from net_stock_group")
    String selectMaxCode();
}
