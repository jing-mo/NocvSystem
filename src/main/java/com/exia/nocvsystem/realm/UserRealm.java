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
import org.apache.shiro.util.ByteSource;
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
        Object username=user.getUsername();
        Object password=user.getPassword();
        if(user!=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    username,
                    password,
                    ByteSource.Util.bytes(user.getSalt()),
                    this.getName() // realm的名字
            );

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
