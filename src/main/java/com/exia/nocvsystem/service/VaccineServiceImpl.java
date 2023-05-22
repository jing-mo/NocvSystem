package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.VaccineMapper;
import com.exia.nocvsystem.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/23 9:47
 */
@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService{
    @Autowired
    VaccineMapper vaccineMapper;
    @Override
    public void autoIncrement() {
        vaccineMapper.autoIncrement();
    }
}
