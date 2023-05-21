package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.RoleMapper;
import com.exia.nocvsystem.dao.UserMapper;
import com.exia.nocvsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/6 18:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        roleMapper.deleteRoleUserByUid(uid);
        if(ids!=null&&ids.length>0){
            for(Integer rid: ids){
                roleMapper.saveUserRole(uid,rid);
            }
        }
    }
    public String findTeacher(String teacherId){
        return userMapper.queryUidByTeacherId(teacherId).get(0);
    }

    @Override
    public boolean isTrueInstitude(Integer classId,Integer institudeId) {
        if(userMapper.FindInstitude(classId).get(0).equals(institudeId))
            return true;
        return false;
    }

    @Override
    public boolean isExistsCardId(String cardId) {
        if(userMapper.FineCardId(cardId).get(0)!=null)
            return true;
        return false;
    }

    @Override
    public boolean isExistsUser(String cardId) {
        if(!userMapper.FindUser(cardId).get(0).equals(null))
            return true;
        return false;
    }

    @Override
    public boolean isExistsDean(Integer uid) {
        Integer institudeId=userMapper.userInstitude(uid).get(0);
        Integer deanNum=userMapper.countOfDean(institudeId);
        Integer deanId=userMapper.idOfDean(institudeId).get(0);
        if(deanNum>=1 && deanId!=uid)
            return true;
        return false;
    }


}
