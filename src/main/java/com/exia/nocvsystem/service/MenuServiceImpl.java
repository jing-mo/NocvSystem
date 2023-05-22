package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.MenuMapper;
import com.exia.nocvsystem.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public void autoIncrement() {
        menuMapper.autoIncrement();
    }
}
