package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/27 20:05
 */
@TableName("approval_process")
@Data
public class ApprovalProcess {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String reason;
    private String address;//原因
    private Integer day;
    private String phone;
    private String nodeStatus;
    private Date createTime;
    private Date updateTime;
    @TableField(exist=false)
    private String username;
}
