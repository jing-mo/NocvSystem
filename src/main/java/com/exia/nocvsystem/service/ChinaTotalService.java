package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.ChinaTotal;

public interface ChinaTotalService extends IService<ChinaTotal> {

    public Integer maxID();
    public void autoIncrement();
}
