package cn.hzskt.bdtg.financial.mapper;

import cn.hzskt.bdtg.financial.domain.OrderCharge;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface OrderChargeMapper extends Mapper<OrderCharge>{
    @Select("select * from financial_order_charge where  valid='1' and order_status like CONCAT_WS('%',#{param3},'%') and order_type like CONCAT_WS('%',#{param2},'%') and username like CONCAT_WS('%',#{param1},'%') order by pay_time desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "orderType", column = "order_type"),
            @Result(property = "username", column = "username"),
            @Result(property = "orderStatus", column = "order_status"),
            @Result(property = "payMoney", column = "pay_money"),
            @Result(property = "payTime", column = "pay_time")
    })
    public List<OrderCharge> orderByTime(String a,String b,String c);
}
