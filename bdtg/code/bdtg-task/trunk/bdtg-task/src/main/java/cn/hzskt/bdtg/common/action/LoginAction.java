package cn.hzskt.bdtg.common.action;

import net.ryian.core.SystemConfig;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allenwc on 14/11/12.
 */
@Controller
public class LoginAction {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest req,Model model) {
        model.addAttribute("application_name", SystemConfig.INSTANCE.getValue("application_name"));
        String exceptionClassName = (String)req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String error = null;
        if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误:"+exceptionClassName;
        }
        model.addAttribute("error",error);
        return "login";
    }

}
