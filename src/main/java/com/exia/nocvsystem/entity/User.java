package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.shiro.crypto.hash.SimpleHash;

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

    //非数据库列 班级名
    @TableField(exist=false)
    private String className;
    //非数据库列 学院名
    @TableField(exist=false)
    private String institudeName;
    //非数据库列 老师名
    @TableField(exist=false)
    private String teacherName;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
