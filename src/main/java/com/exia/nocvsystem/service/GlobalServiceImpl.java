package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.GlobalDataMapper;
import com.exia.nocvsystem.entity.LineTrend;
import com.exia.nocvsystem.entity.NocvGlobalData;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GlobalServiceImpl extends ServiceImpl<GlobalDataMapper,NocvGlobalData> implements GlobalService{
}
