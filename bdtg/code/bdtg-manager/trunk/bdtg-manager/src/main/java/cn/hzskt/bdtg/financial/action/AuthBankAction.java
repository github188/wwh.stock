package cn.hzskt.bdtg.financial.action;

import cn.hzskt.bdtg.common.constants.DictKeys;
import cn.hzskt.bdtg.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.financial.domain.AuthBank;
import cn.hzskt.bdtg.financial.service.AuthBankService;

import javax.servlet.http.HttpServletRequest;

/**
*
* @description:AuthBank action
* @author: autoCode
* @history:
*/

@Controller
@RequestMapping("/financial/auth-bank")
@SuppressWarnings("serial")
public class AuthBankAction extends MagicAction<AuthBank,AuthBankService> {
    @Autowired
    DictService dictService;
    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("authstatus",dictService.getDictsByKey(DictKeys.AUTH_STATUS).values());
    }

}
