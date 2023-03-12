package com.exia.nocvsystem.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm{
    @Autowired
    private LoginService loginService;
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("username", token.getPrincipal().toString());
        User user = loginService.getOne(queryWrapper);
        if(user!=null){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),this.getName());
            return info;
        }
        return null;
    }
    //授权
    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
