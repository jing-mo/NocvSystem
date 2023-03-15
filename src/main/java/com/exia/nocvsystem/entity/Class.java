package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("class")
public class Class {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer institudeId;

}
