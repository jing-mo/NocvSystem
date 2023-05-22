package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/8 21:32
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {
    @Update("ALTER TABLE class AUTO_INCREMENT = 0;")
    void autoIncrement();
}
