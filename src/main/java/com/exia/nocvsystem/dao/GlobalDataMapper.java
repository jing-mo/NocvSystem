package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.entity.NocvGlobalData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalDataMapper extends BaseMapper<NocvGlobalData> {

}
