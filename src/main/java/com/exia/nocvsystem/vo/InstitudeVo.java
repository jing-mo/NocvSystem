package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.Institude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/18 0:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitudeVo extends Institude {
    private Integer page=1;
    private Integer limit=10;
}
