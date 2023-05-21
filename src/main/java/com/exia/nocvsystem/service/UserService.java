package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.User;

public interface UserService extends IService<User> {
    void saveUserRole(Integer uid, Integer[] ids);
    String findTeacher(String teacherId);

    boolean isTrueInstitude(Integer classId, Integer institudeId);

    boolean isExistsCardId(String cardId);
    boolean isExistsUser(String cardId);
    boolean isExistsDean(Integer uid);
}
