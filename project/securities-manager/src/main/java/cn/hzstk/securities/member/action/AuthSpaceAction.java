package cn.hzstk.securities.member.action;

import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.sys.service.DictService;
import net.ryian.orm.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;

import javax.servlet.http.HttpServletRequest;

/**
*
* @description:AuthSpace action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/member/auth-space")
@SuppressWarnings("serial")
public class AuthSpaceAction extends MagicAction<AuthSpace,AuthSpaceService> {
    @Autowired
    DictService dictService;

    protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {
        model.addAttribute("indusid",dictService.getDictsByKey(DictKeys.INDUSID).values());
    }

}
