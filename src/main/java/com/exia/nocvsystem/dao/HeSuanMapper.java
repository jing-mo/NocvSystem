package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.HeSuan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HeSuanMapper extends BaseMapper<HeSuan> {
    @Update("ALTER TABLE hesuan AUTO_INCREMENT = 0;")
    void autoIncrement();
}
