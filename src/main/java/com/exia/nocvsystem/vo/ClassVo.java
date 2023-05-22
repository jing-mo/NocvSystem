package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.Institude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/18 8:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassVo extends Institude {
    private Integer page=1;
    private Integer limit=10;
}
