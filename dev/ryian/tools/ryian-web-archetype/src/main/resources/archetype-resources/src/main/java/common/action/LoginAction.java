#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.common.action;

        import net.ryian.core.SystemConfig;
        import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by allenwc on 14/11/12.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginAction {

    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("application_name", SystemConfig.INSTANCE.getValue("application_name"));
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        model.addAttribute("application_name", SystemConfig.INSTANCE.getValue("application_name"));
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        return "login";
    }

}
