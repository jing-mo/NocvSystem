package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.ChinaTotalMapper;
import com.exia.nocvsystem.entity.ChinaTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper, ChinaTotal> implements ChinaTotalService{
    @Autowired
    private ChinaTotalMapper chinaTotalMapper;
    @Override
    public Integer maxID() {
        return chinaTotalMapper.maxID();
    }

    @Override
    public void autoIncrement() {
        chinaTotalMapper.autoIncrement();
    }

}
