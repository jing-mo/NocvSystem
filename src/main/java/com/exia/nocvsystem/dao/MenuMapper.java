package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Update("ALTER TABLE menu AUTO_INCREMENT = 0;")
    void autoIncrement();
}
