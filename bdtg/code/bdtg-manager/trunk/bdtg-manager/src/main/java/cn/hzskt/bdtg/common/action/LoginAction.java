package cn.hzskt.bdtg.common.action;

import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import net.ryian.core.SystemConfig;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 14/11/12.
 */
@Controller
public class LoginAction {

    @Autowired
    private UserService userService;

    @Login(action = Action.Skip)
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

    @Login(action = Action.Skip)
    @RequestMapping(value = "/loginpost")
    public String loginpost(HttpServletRequest request, HttpServletResponse response,String username, String password) {
        User user = userService.validate(username,password);
        if (user != null) {
            SSOToken st = new SSOToken(request);
            st.setUid(user.getId().toString());
            st.setData(JSON.toJSONString(user));
            SSOHelper.setSSOCookie(request, response, st, true);
            return "redirect:/";
        }
        return "login";
    }

}
