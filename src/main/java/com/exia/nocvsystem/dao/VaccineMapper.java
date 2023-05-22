package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.Vaccine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VaccineMapper extends BaseMapper<Vaccine> {
    @Update("ALTER TABLE vaccine AUTO_INCREMENT = 0;")
    void autoIncrement();
}
