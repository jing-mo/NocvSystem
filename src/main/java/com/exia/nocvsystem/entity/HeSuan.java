package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/22 21:28
 */
@Data
@TableName("hesuan")
public class HeSuan {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String phone;
    private String card;
    private String type;
    private String result;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
