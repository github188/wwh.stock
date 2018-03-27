package cn.hzstk.securities.net.mapper;

import cn.hzstk.securities.net.domain.FundDetails;
import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
@Controller("net-fund-details-mapper")
public interface FundDetailsMapper extends Mapper<FundDetails>{
}
