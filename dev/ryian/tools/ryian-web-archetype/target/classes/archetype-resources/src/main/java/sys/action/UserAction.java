#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.action;

        import ${package}.common.action.MagicAction;
        import ${package}.sys.domain.User;
        import ${package}.sys.service.UserService;
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
    public String selectRoles(Model model, @RequestParam(value = "userId") String userId) {
        model.addAttribute("userId", userId);
        return "/sys/user/selectRoles";
    }

}
