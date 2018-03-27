package cn.hzstk.securities.financial.mapper;

import cn.hzstk.securities.financial.domain.Finance;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.domain.Withdraw;
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
    @Select("select * from financial_withdraw  where valid='1' and uid=#{value} and withdraw_cash is not NULL order by applic_time desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "payUsername", column = "pay_username"),
            @Result(property = "username", column = "username"),
            @Result(property = "withdrawStatus", column = "withdraw_status"),
            @Result(property = "withdrawCash", column = "withdraw_cash"),
            @Result(property = "applicTime", column = "applic_time")
    })
    public List<Withdraw> orderByTimeAllDown(String id);

    @Select("select * from financial_withdraw  where valid='1' and uid=#{value} and withdraw_cash is not NULL order by applic_time ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "payType", column = "pay_type"),
            @Result(property = "payUsername", column = "pay_username"),
            @Result(property = "username", column = "username"),
            @Result(property = "withdrawStatus", column = "withdraw_status"),
            @Result(property = "withdrawCash", column = "withdraw_cash"),
            @Result(property = "applicTime", column = "applic_time")
    })
    public List<Withdraw> orderByTimeAllUp(String id);

}
