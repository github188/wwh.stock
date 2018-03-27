package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.Plate;
import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@Controller("net-plate-mapper")
public interface PlateMapper extends Mapper<Plate>{
}
