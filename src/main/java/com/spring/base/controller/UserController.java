package com.spring.base.controller;

import com.spring.base.entity.User;
import com.spring.base.form.UserLoginForm;
import com.spring.base.service.impl.UserServiceImpl;
import com.spring.base.system.ResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/5
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    public String login(Model model,UserLoginForm form,boolean rememberMe){
        System.out.println(form);
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if (user != null) {
            //清除session
            SecurityUtils.getSubject().logout();
        }
        //2 判断当前用户是否登录
        if (!currentUser.isAuthenticated()) {
            try {

                user = userService.getByLoginName(form.getLoginName());
                if (null == user) {
                    model.addAttribute("msg", "该用户不存在，请联系管理员添加");
                    return "login";
                }

                UsernamePasswordToken token = new UsernamePasswordToken(form.getLoginName(), form.getPassword());
                token.setRememberMe(rememberMe);
                token = new UsernamePasswordToken(user.getLoginName(), form.getPassword());
                //登录认证
                currentUser.login(token);
            } catch (UnknownAccountException | IncorrectCredentialsException ue) {
                ue.printStackTrace();
                return loginFailed(model, ue.getMessage(), form);
            } catch (AuthenticationException ae) {
                ae.printStackTrace();
                return loginFailed(model, ae.getMessage(), form);
            }
        }
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("jsessionid", session.getId());
        return "redirect:" + "/user/index";
    }

    private String loginFailed(Model model, String loginMsg, UserLoginForm form) {
        model.addAttribute("loginMsg", loginMsg);
        model.addAttribute("msg", "用户名或密码错误");
        model.addAttribute("form", form);
        return "login";
    }
}
