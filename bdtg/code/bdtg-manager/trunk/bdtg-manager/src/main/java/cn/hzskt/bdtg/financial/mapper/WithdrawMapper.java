package cn.hzskt.bdtg.financial.mapper;

import cn.hzskt.bdtg.financial.domain.Withdraw;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface WithdrawMapper extends Mapper<Withdraw>{
    @Select("select * from financial_withdraw where  valid='1' and username like CONCAT_WS('%',#{param1},'%') and pay_account like CONCAT_WS('%',#{param2},'%') and withdraw_cash like CONCAT_WS('%',#{param3},'%') and withdraw_status like CONCAT_WS('%',#{param4},'%')order by applic_time desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "payType", column = "pay_type"),
            @Result(property = "payUsername", column = "pay_username"),
            @Result(property = "username", column = "username"),
            @Result(property = "withdrawStatus", column = "withdraw_status"),
            @Result(property = "withdrawCash", column = "withdraw_cash"),
            @Result(property = "applicTime", column = "applic_time"),
            @Result(property = "payAccount", column = "pay_account")
    })
    public List<Withdraw> orderByTime(String a,String b,String c,String d);
}
