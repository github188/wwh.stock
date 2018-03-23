package cn.hzskt.bdtg.member.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.member.domain.AuthEmail;
import cn.hzskt.bdtg.member.service.AuthEmailService;

/**
*
* @description:AuthEmail action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/member/auth-email")
@SuppressWarnings("serial")
public class AuthEmailAction extends MagicAction<AuthEmail,AuthEmailService> {


}
