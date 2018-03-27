package cn.hzstk.securities.stockeast.mapper;

import cn.hzstk.securities.stockeast.domain.SelfSelect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
public interface SelfSelectMapper extends Mapper<SelfSelect>{
    @Select("select distinct stock_code from stk_self_select where valid = '1' and dt = #{value}")
    List<String> selectStockCode(String dt);

    @Delete("DELETE FROM stk_self_select where dt = #{value}")
    void deleteByGroup(String dt);
}
