package cn.hzstk.securities.stk.mapper;

import cn.hzstk.securities.stk.domain.InvestCalendar;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface InvestCalendarMapper extends Mapper<InvestCalendar>{
    @Select("select a1.*,a2.stock_name from stk_invest_calendar a1," +
            "(select * from stk_hq_details where dt in (select max(dt) from stk_hq_details where valid = '1')) a2 " +
            "where a1.valid='1' and a1.stock_code=a2.stock_code and a1.dt=#{value}")
    @Results(value = {
            @Result(property = "stockCode", column = "stock_code"),
            @Result(property = "investType", column = "invest_type"),
            @Result(property = "stockName", column = "stock_name")
    })
    List<InvestCalendar> getInvestCalendar(String example);
}
