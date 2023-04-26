package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.HeSuan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/22 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HeSuanVo extends HeSuan {
    private Integer page;
    private Integer limit;
}
