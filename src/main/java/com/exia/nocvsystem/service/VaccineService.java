package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.Vaccine;
import org.springframework.stereotype.Service;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/23 9:47
 */
public interface VaccineService extends IService<Vaccine> {
    public void autoIncrement();
}
