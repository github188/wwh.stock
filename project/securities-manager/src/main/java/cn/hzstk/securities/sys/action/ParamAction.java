package cn.hzstk.securities.sys.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.sys.domain.Param;
import cn.hzstk.securities.sys.service.ParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenwc on 16/3/10.
 */

@Controller
@RequestMapping("/sys/param")
public class ParamAction extends MagicAction<Param,ParamService> {
}
