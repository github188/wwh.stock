package cn.hzskt.bdtg.portal.action;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import com.baomidou.kisso.SSOHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 16/4/21.
 */
@Controller
public class LogoutAction extends BaseMagicAction{

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SSOHelper.clearLogin(request,response);
        printJson(response,messageSuccuseWrap());
    }

}
