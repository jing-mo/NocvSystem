package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.ClassMapper;
import com.exia.nocvsystem.entity.Class;
import org.springframework.stereotype.Service;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/8 21:39
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService{
}
