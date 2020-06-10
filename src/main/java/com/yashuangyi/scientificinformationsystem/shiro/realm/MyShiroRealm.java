package com.yashuangyi.scientificinformationsystem.shiro.realm;

import com.yashuangyi.scientificinformationsystem.modules.admin.entity.Admin;
import com.yashuangyi.scientificinformationsystem.modules.admin.service.AdminService;
import com.yashuangyi.scientificinformationsystem.modules.user.entity.User;
import com.yashuangyi.scientificinformationsystem.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 1:26
 */
@Component
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    AdminService adminServiceImpl;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */

    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.out.println("doGetAuthenticationInfo...");
        String no = (String) token.getPrincipal();
        log.info(token.getPrincipal().toString());
        //1. 根据no，查询用户信息
        Admin admin = adminServiceImpl.findByAccount(no);
        if (admin != null){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, admin.getPassword(), this.getName());
            session.setAttribute("identity", "admin");
            return info;
        }

        User user = userServiceImpl.findByAccount(no);
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            // return null;
            throw new UnknownAccountException("用户不存在!");
        }
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        session.setAttribute("identity", "user");
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}

