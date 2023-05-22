package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.entity.NocvGlobalData;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalDataMapper extends BaseMapper<NocvGlobalData> {
    @Select("select * from nocv_global_data order by id desc limit 198")
    List<NocvGlobalData> listOrOrderByLimit198();
    @Update("ALTER TABLE nocv_globle_data AUTO_INCREMENT = 0;")
    void autoIncrement();
}
