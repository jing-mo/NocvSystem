package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.NocvNews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/26 21:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NocvNewsVo extends NocvNews {
    private Integer page=1;
    private Integer limit=10;
}
