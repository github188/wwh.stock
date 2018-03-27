package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.PlateStock;
import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@Controller("net-plate-stock-mapper")
public interface PlateStockMapper extends Mapper<PlateStock>{
}
