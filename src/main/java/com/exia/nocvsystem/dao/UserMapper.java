package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
