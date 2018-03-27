package cn.hzstk.securities.stk.mapper;

import cn.hzstk.securities.stk.domain.Plate;
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
@SuppressWarnings("JavaDoc")
public interface PlateMapper extends Mapper<Plate>{

    @Select("select * from stk_plate where valid='1' and dt in (select max(dt) from stk_plate where valid='1')")
    @Results(value = {
            @Result(property = "plateCode", column = "plate_code"),
            @Result(property = "plateName", column = "plate_name")
    })
    List<Plate> getAllPlate();
}
