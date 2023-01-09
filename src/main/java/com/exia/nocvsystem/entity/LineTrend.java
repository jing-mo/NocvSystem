package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("line_Trend")
public class LineTrend {
    private Integer id;
    private Integer confirm;
    private Integer isolation;
    private Integer cure;
    private Integer dead;
    private Integer similar;
    private Date createTime;

}
