package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.MutablePropertyValues;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select card_id from user where class_id = #{classId}")
    List<String> queryCardIdByClassId(Integer classId);

    @Select("select u.username from user u,approval_process a where u.id = a.uid and a.uid=#{uid}")
    List<String> queryUidName(Integer uid);

    @Select("select card_id from user where institude_id = #{institudeId}")
    List<String> queryCardIdByInstitudeId(Integer institudeId);


    @Select("select id from user where class_id = #{classId}")
    List<Integer> queryUidByClassId(Integer classId);


    @Select("select id from user where institude_id = #{institudeId}")
    List<Integer> queryUidByInstitudeId(Integer institudeId);

    @Select("select username from user where card_id = #{teacherId}")
    List<String> queryUidByTeacherId(String teacherId);
    @Select("select card_id from user where card_id = #{cardId}")
    List<String> FineCardId(String cardId);
    @Select("select * from user where card_id = #{cardId}")
    List<User> FindUser(String cardId);
    @Select("select institude_id from class where id = #{classId}")
    List<Integer> FindInstitude(Integer classId);

    @Select("select institude_id from user where id=#{id}")
    List<Integer> userInstitude(Integer id);
    @Select("select count(*) from user,user_role,institude where institude.id=#{institudeId} and user_role.rid=4 and user_role.uid =user.id and institude.id=user.institude_id")
    Integer countOfDean(Integer institudeId);
    @Select("select user.id from user,user_role,institude where institude.id=#{institudeId} and user_role.rid=4 and user_role.uid =user.id and institude.id=user.institude_id")
    List<Integer> idOfDean(Integer institudeId);
    @Update("ALTER TABLE user AUTO_INCREMENT = 0;")
    void autoIncrement();
}
