package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/6 14:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends User {
    private Integer page=1;
    private Integer limit=10;
}
