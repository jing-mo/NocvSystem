package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.ApprovalProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/27 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalVo extends ApprovalProcess {
    private Integer page;
    private Integer limit;
}
