package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.HealthClock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface HealthClockMapper extends BaseMapper<HealthClock> {
    @Update("ALTER TABLE health_clock AUTO_INCREMENT = 0;")
    void autoIncrement();

}
