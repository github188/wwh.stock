package cn.hzstk.securities.portal.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 16/3/31.
 */
@Controller
@RequestMapping("/")
public class IndexAction extends BaseMagicAction {
    @Autowired
    private UserService userService;

    @RequestMapping
    public String index(HttpServletRequest request) {
        return "index";
    }



}
