package com.yashuangyi.scientificinformationsystem.shiro.controller;

import com.yashuangyi.scientificinformationsystem.modules.user.service.UserService;
import com.yashuangyi.scientificinformationsystem.modules.admin.service.AdminService;
import com.yashuangyi.scientificinformationsystem.modules.user.vo.LoginVo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * @author yashuangyi
 * @version 1.0
 * @content 登录控制器
 * @date 2020-04-21 1:14
 */
@Controller
public class LoginController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    AdminService adminServiceImpl;

    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public SisResult login(@RequestBody LoginVo loginVo){
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();

        if(StringUtils.isEmpty(loginVo.getAccount()) || StringUtils.isEmpty(loginVo.getPassword())){
            return SisResult.build(401,"账号或密码错误");
        }

        // 3、将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getAccount(), loginVo.getPassword());

        // 4、认证
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            if(session.getAttribute("identity").equals("admin")){
                session.setAttribute("id", adminServiceImpl.findByAccount(loginVo.getAccount()).getId());
                return SisResult.build(201,"管理员登录", adminServiceImpl.findByAccount(loginVo.getAccount()).getId());
            }
            session.setAttribute("userId", userServiceImpl.findByAccount(loginVo.getAccount()).getId());
            return SisResult.build(200,"用户登录", userServiceImpl.findByAccount(loginVo.getAccount()).getId());
        }catch (UnknownAccountException e) {
            //e.printStackTrace();
            return SisResult.build(404,"账号不存在");
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            return SisResult.build(404,"密码错误");
        }

    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }


}
