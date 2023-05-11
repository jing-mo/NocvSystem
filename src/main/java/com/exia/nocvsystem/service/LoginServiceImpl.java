package com.exia.nocvsystem.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.LoginMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, User> implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public User login(String username,String password){
        return loginMapper.login(username, password);
    }


}
