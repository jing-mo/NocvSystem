package com.exia.nocvsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exia.nocvsystem.dao.RoleMapper;
import com.exia.nocvsystem.dao.UserMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础controller
 */
public class BaseController {

    private User getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加数据权限
     * @param queryWrapper
     */
    protected void addCard(QueryWrapper queryWrapper){
        //找到当前登录的用户
        User user = getCurrentUser();
        System.out.println(JSONObject.toJSONString(user));
        Integer dataAuthority = roleMapper.queryDataAuthority(user.getId());
        if(dataAuthority==4){
            return;
        }
        //如果是自己，就只查学号是自己的
        if(dataAuthority==1){
            queryWrapper.eq("card",user.getCardId());
        }
        //如果是同班级，就查同班级学号的
        if(dataAuthority==2){
            List<String> cardIds = userMapper.queryCardIdByClassId(user.getClassId());
            queryWrapper.in("card",cardIds);
        }
        //如果是同学院，就查同学院学号的
        if(dataAuthority==3){
            List<String> cardIds = userMapper.queryCardIdByInstitudeId(user.getInstitudeId());
            queryWrapper.in("card",cardIds);
        }
    }
    /**
     * 添加数据权限
     * @param queryWrapper
     */
    protected void addUids(QueryWrapper queryWrapper){
        User user = getCurrentUser();
        System.out.println(JSONObject.toJSONString(user));
        Integer dataAuthority = roleMapper.queryDataAuthority(user.getId());
        if(dataAuthority==4){
            return;
        }
        //如果是自己，就只查学号是自己的
        if(dataAuthority==1){
            queryWrapper.eq("uid",user.getId());
        }
        //如果是同班级，就查同班级学号的
        if(dataAuthority==2){
            List<Integer> cardIds = userMapper.queryUidByClassId(user.getClassId());
            queryWrapper.in("uid",cardIds);
        }
        //如果是同学院，就查同学院学号的
        if(dataAuthority==3){
            List<Integer> cardIds = userMapper.queryUidByInstitudeId(user.getInstitudeId());
            queryWrapper.in("uid",cardIds);
        }

    }


}
