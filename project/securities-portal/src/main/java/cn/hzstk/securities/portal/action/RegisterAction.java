package cn.hzstk.securities.portal.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import net.ryian.core.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allenwc on 16/4/21.
 */
@Controller
public class RegisterAction extends BaseMagicAction{

    @Autowired
    private UserService userService;
    @Autowired
    AuthSpaceService authSpaceService;
    /**
     * 注册页面
     * @return String
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册保存处理
     * @return String
     */
    @RequestMapping("/registersave")
    public void registersave(HttpServletRequest request,HttpServletResponse response) {
        try {

            User user =  bindEntity(request, User.class);
            user.setName(user.getUserName());
            user.encryptUserPassword(user.getPassword());
            Long userid= userService.saveOrUpdate(user);
            //用户扩展表
            AuthSpace au = new AuthSpace();
            SecureRandom random = new SecureRandom();
            int intuserpic=random.nextInt(20);
            au.setUserPic("/static/img/sys_img/"+intuserpic+".jpg");
            au.setUserName(user.getName());
            au.setUserId(userid);
            au.setPassword(user.getPassword());
            au.setSecCode(user.getPassword());
//            au.setMobile(request.getParameter("mobile"));
//            au.setEmail(request.getParameter("email"));
            au.setRegTime(new Date());
            au.setUserType(0);
            au.setAuthStatus(0);
            au.setEmailStatus(0);
            au.setMobileStatus(0);
            authSpaceService.saveOrUpdate(au);
            User u = userService.get(userid);
            if (u != null) {
                SSOToken st = new SSOToken(request);
                st.setUid(user.getId().toString());
                st.setData(JSON.toJSONString(user));
                SSOHelper.setSSOCookie(request, response, st, true);
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "恭喜您，"+user.getName()+"注册成功");
            data.put("url", SystemConfig.INSTANCE.getValue("portal_url")+"/member/index");
            response.getWriter().write(JSON.toJSONString(data));

        }catch (Exception e){
            e.printStackTrace();
        }
//        return "redirect:/";
    }

}
