package cn.hzstk.securities.sys.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.UserService;
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
@SuppressWarnings({"serial", "JavaDoc"})
public class UserAction extends MagicAction<User, UserService> {

    @RequestMapping(value = "selectRoles")
    public String selectRoles(Model model,@RequestParam(value = "userId")String userId) {
        model.addAttribute("userId",userId);
        return "/sys/user/selectRoles";
    }

}
