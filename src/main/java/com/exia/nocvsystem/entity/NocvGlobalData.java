package com.exia.nocvsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("nocv_global_data")
public class NocvGlobalData {
    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
