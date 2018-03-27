package cn.hzstk.securities.financial.mapper;

import cn.hzstk.securities.financial.domain.Finance;
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
public interface FinanceMapper extends Mapper<Finance>{
        @Select("select * from financial_finance  where valid='1' and fina_type=#{param1} and uid=#{param2} order by id desc")
        @Results(value = {
                @Result(property = "id", column = "id"),
                @Result(property = "finaType", column = "fina_type"),
                @Result(property = "finaAction", column = "fina_action"),
                @Result(property = "finaCash", column = "fina_cash"),
                @Result(property = "userBalance", column = "user_balance"),
                @Result(property = "finaTime", column = "fina_time")
        })
        public List<Finance> orderByIdDown(String finaType,String id);

    @Select("select * from financial_finance  where valid='1'and uid=#{value} order by id desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "finaType", column = "fina_type"),
            @Result(property = "finaAction", column = "fina_action"),
            @Result(property = "finaCash", column = "fina_cash"),
            @Result(property = "userBalance", column = "user_balance"),
            @Result(property = "finaTime", column = "fina_time")
    })
    public List<Finance> orderByIdAll(String id);

    @Select("select * from financial_finance  where valid='1' and fina_type= #{param1} and uid=#{param2} order by fina_time desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "finaType", column = "fina_type"),
            @Result(property = "finaAction", column = "fina_action"),
            @Result(property = "finaCash", column = "fina_cash"),
            @Result(property = "userBalance", column = "user_balance"),
            @Result(property = "finaTime", column = "fina_time")
    })
    public List<Finance> orderByTimeDown(String finaType,String id);

    @Select("select * from financial_finance  where valid='1' and fina_type= #{param1} and uid=#{param2} order by fina_time ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "finaType", column = "fina_type"),
            @Result(property = "finaAction", column = "fina_action"),
            @Result(property = "finaCash", column = "fina_cash"),
            @Result(property = "userBalance", column = "user_balance"),
            @Result(property = "finaTime", column = "fina_time")
    })
    public List<Finance> orderByTimeUp(String finaType,String id);

    @Select("select * from financial_finance  where valid='1' and uid=#{value} order by fina_time desc")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "finaType", column = "fina_type"),
            @Result(property = "finaAction", column = "fina_action"),
            @Result(property = "finaCash", column = "fina_cash"),
            @Result(property = "userBalance", column = "user_balance"),
            @Result(property = "finaTime", column = "fina_time")
    })
    public List<Finance> orderByTimeAllDown(String id);

    @Select("select * from financial_finance  where valid='1' and uid=#{value} order by fina_time ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "finaType", column = "fina_type"),
            @Result(property = "finaAction", column = "fina_action"),
            @Result(property = "finaCash", column = "fina_cash"),
            @Result(property = "userBalance", column = "user_balance"),
            @Result(property = "finaTime", column = "fina_time")
    })
    public List<Finance> orderByTimeAllUp(String id);

}
