package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class Role {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String remark;
    /**
     * 数据权限 1=自己 2=同班级 3=同学院 4=全部
     */
    private Integer dataAuthority;
}
