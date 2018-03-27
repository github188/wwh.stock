package cn.hzstk.securities.portal.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.common.utils.Validator;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import net.ryian.core.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by allenwc on 16/4/21.
 */
@Controller
public class LoginAction extends BaseMagicAction{

    @Autowired
    private UserService userService;
    @Autowired
    private AuthSpaceService authSpaceService;

    @Login(action = Action.Skip)
    @RequestMapping("/login")
    public String login(HttpServletResponse response, String username, String password) {
        return "login";
    }

    @Login(action = Action.Skip)
    @RequestMapping("/loginpost")
    public void loginpost(HttpServletRequest request, HttpServletResponse response, String username, String password)throws Exception{
        User user = userService.validate(getuserName(username),password);
        Map<String, Object> data = new HashMap<String, Object>();
        if (user != null) {
            user.setLastLoginDate(new Date());
            userService.saveOrUpdate(user);
            SSOToken st = new SSOToken(request);
            st.setUid(user.getId().toString());
            st.setData(JSON.toJSONString(user));
            SSOHelper.setSSOCookie(request, response, st, true);
            data.put("status", "success");
            data.put("data", "登录成功");
            data.put("url", SystemConfig.INSTANCE.getValue("portal_url"));
            //return "redirect:/";
        }else {
            data.put("status", "fail");
            data.put("data", "登录失败");
            data.put("url", SystemConfig.INSTANCE.getValue("portal_url") + "/login");
        }
        response.getWriter().write(JSON.toJSONString(data));
        //return "login";
    }
    /**
     * 用户名或密码是否存在
     *
     */
    @Login(action = Action.Skip)
    @RequestMapping(value="pass_right")
    public void pass_right(HttpServletRequest request,HttpServletResponse rep,String name,String passwd) {
        try {
            Map<String, String> param = new HashMap<String, String>();
            String userName=getuserName(name);
            if(userName.equals("")){
                param.put("vars", "N");
            }else{
                User user = userService.findUserByUserName(userName);
                String oldPd = user.getPassword();
                user.encryptUserPassword(passwd);
                if(oldPd.equals(user.getPassword())){
                    param.put("vars", "Y");
                }else {
                    param.put("vars", "N");
                }
            }
            this.printText(rep, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //获取用户名
    private String getuserName(String name){
        try {
            List<AuthSpace> authSpaces = new ArrayList<>();
            if(Validator.isEmail(name)){
                authSpaces = authSpaceService.querybyemail(name);
            }else if(Validator.isMobile(name)){
                authSpaces = authSpaceService.querybymobile(name);
            }else {
                authSpaces = authSpaceService.querybyusername(name);
            }
            if(authSpaces.size()>0){
                return  authSpaces.get(0).getUserName();
            }else {
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
