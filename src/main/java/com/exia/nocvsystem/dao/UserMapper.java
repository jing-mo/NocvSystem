package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select card_id from user where class_id = #{classId}")
    List<String> queryCardIdByClassId(Integer classId);



    @Select("select card_id from user where institude_id = #{institudeId}")
    List<String> queryCardIdByInstitudeId(Integer institudeId);


    @Select("select id from user where class_id = #{classId}")
    List<Integer> queryUidByClassId(Integer classId);


    @Select("select id from user where institude_id = #{institudeId}")
    List<Integer> queryUidByInstitudeId(Integer institudeId);

}
