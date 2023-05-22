package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface LoginMapper extends BaseMapper<User> {
//    @Select("select * from user where username= #{username} and password= #{password}")
//    public User login(String username,String password);

    @Select("select salt from user where card_id= #{cardId}")
    String FindSalt(String cardId);

}
