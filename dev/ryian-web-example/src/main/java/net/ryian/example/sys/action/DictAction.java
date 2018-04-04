package net.ryian.example.sys.action;

import net.ryian.example.common.action.MagicAction;
import net.ryian.example.sys.domain.Dict;
import net.ryian.example.sys.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/dict")
public class DictAction extends MagicAction<Dict, DictService> {


}
