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
 * Create by 2023/4/23 9:43
 */
@TableName("vaccine")
@Data
public class Vaccine {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String phone;
    private String card;
    private String zhenci;//针刺
    private String pici;//批次
    private String changjia;//厂家
    private String danwei;//单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
