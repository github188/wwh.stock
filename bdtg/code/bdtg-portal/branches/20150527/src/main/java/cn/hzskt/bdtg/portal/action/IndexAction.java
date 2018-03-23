package cn.hzskt.bdtg.portal.action;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.UserService;
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
