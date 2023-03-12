package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user")
@Data
public class User {
    @TableId(value="id",type= IdType.AUTO)
    private  Integer id;
    private String username;
    private String password;
    private String salt;
    private String sex;
    private Integer age;
    private String address;
    private String img;
    private String phone;
    private String cardId;
    //作为外键使用
    private Integer classId;
    private Integer institudeId;
    private Integer teacherId;
    private Integer roleId;
}
