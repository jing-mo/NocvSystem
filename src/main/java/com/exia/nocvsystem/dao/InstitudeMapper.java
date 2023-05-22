package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.Institude;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InstitudeMapper extends BaseMapper<Institude> {
    @Update("ALTER TABLE institude AUTO_INCREMENT = 0;")
    void autoIncrement();
}
