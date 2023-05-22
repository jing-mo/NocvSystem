package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.HealthClockMapper;
import com.exia.nocvsystem.entity.HealthClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper, HealthClock> implements HealthClockService{
    @Autowired
    HealthClockMapper healthClockMapper;
    @Override
    public void autoIncrement() {
        healthClockMapper.autoIncrement();
    }
}
