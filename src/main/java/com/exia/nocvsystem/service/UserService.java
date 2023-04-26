package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.User;

public interface UserService extends IService<User> {
    void saveUserRole(Integer uid, Integer[] ids);
}
