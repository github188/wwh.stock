package cn.hzskt.bdtg.sys.action;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:User action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/user")
@SuppressWarnings("serial")
public class UserAction extends MagicAction<User, UserService> {

    @RequestMapping(value = "selectRoles")
    public String selectRoles(Model model,@RequestParam(value = "userId")String userId) {
        model.addAttribute("userId",userId);
        return "/sys/user/selectRoles";
    }

}
