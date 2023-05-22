package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.HealthClock;

public interface HealthClockService extends IService<HealthClock> {
    public void autoIncrement();
}
