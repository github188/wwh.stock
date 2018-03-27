package cn.hzstk.securities.financial.action;

import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.financial.domain.AuthBank;
import cn.hzstk.securities.financial.service.AuthBankService;

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
