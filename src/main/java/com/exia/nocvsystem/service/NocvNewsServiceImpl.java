package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.NocvNewsMapper;
import com.exia.nocvsystem.dao.RoleMapper;
import com.exia.nocvsystem.entity.NocvNews;
import com.exia.nocvsystem.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/16 17:55
 */
@Service
public class NocvNewsServiceImpl extends ServiceImpl<NocvNewsMapper, NocvNews> implements NocvNewsService{
    @Autowired
    private NocvNewsMapper nocvNewsMapper;
    @Override
    public List<NocvNews> listNewsLimit5() {
        return nocvNewsMapper.listNewsLimit5();
    }

    @Override
    public void autoIncrement() {
        nocvNewsMapper.autoIncrement();
    }
}
