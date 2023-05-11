package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface LoginService extends IService<User> {
    User login(String username, String password);

}
