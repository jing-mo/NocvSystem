package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/26 21:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineVo extends Vaccine {
    private Integer page=1;
    private Integer limit=10;
}
