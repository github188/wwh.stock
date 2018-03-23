package cn.hzskt.bdtg.sys.action;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.sys.domain.Param;
import cn.hzskt.bdtg.sys.service.ParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenwc on 16/3/10.
 */

@Controller
@RequestMapping("/sys/param")
public class ParamAction extends MagicAction<Param,ParamService> {
}
