package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/3/16 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleView extends Role {
    private Integer page=1;
    private Integer limit=10;
}
