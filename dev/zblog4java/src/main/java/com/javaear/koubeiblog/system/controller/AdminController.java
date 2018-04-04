package com.javaear.koubeiblog.system.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.javaear.koubeiblog.CacheController;
import com.javaear.koubeiblog.system.entity.dto.IndexDTO;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.entity.model.CommentModel;
import com.javaear.koubeiblog.system.entity.model.TagModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author aooer
 */
@Controller
@RequestMapping("system")
public class AdminController extends CacheController {

    @RequestMapping("index")
    public void index(ModelMap modelMap) {
        IndexDTO indexDTO = new IndexDTO();
        indexDTO.setCurrentVersion(cacheContext.getCacheDTO().getVersion());
        indexDTO.setCateCount(cacheContext.getCacheDTO().getCategoryModelList().size());
        indexDTO.setArticleCount(generalMapper.selectCount(new ArticleModel()));
        indexDTO.setCommentCount(generalMapper.selectCount(new CommentModel()));
        indexDTO.setTagCount(generalMapper.selectCount(new TagModel()));
        indexDTO.setUserCount(generalMapper.selectCount(new UserModel()));
        modelMap.put("indexDTO", indexDTO);
        modelMap.put("title", "后台首页");
    }

    @Login(action = Action.Skip)
    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        if (SSOHelper.getToken(request) != null)
            return "redirect:index.html";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String savedate = request.getParameter("savedate");
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            UserModel userModel = generalMapper.selectOne(new UserModel(username));
            if (userModel != null && userModel.getPassword().equals(password)) {
                //authSSOCookie 设置 cookie 同时改变 jsessionId
                SSOToken ssoToken = new SSOToken(request);
                ssoToken.setData(JSON.toJSONString(userModel));
                //记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
                if (savedate.equals("1"))
                    request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, TimeUnit.DAYS.toSeconds(7));
                SSOHelper.setSSOCookie(request, response, ssoToken, true);
                return "redirect:index.html";
            }
        }
        return "system/login";
    }

    @Login(action = Action.Skip)
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SSOHelper.clearLogin(request, response);
        return "redirect:login.html";
    }

    @Login(action = Action.Skip)
    @RequestMapping(value = "unpermission", method = RequestMethod.GET)
    public void unpermission() {
    }

    /**
     * 功能完善后会删除此方法
     */
    @RequestMapping("management/*-management")
    public void list() {
    }
}
