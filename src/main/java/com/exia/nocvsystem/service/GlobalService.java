package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.LineTrend;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.entity.NocvGlobalData;

import java.util.List;

public interface GlobalService extends IService<NocvGlobalData> {
    List<NocvGlobalData> listOrOrderByLimit198();
    public void autoIncrement();
}
