package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/16 17:39
 */
@Data
@TableName("nocv_news")
public class NocvNews {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String publishby;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }
}
