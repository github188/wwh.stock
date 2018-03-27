package cn.hzstk.securities.sys.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.sys.domain.Dict;
import cn.hzstk.securities.sys.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/dict")
public class DictAction extends MagicAction<Dict, DictService> {

}
