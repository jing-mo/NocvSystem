package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.NocvNews;
import com.exia.nocvsystem.service.NocvNewsService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NocvNewsMapper extends BaseMapper<NocvNews> {
    @Select("select * from nocv_news order by create_time desc limit 5")
    List<NocvNews> listNewsLimit5();
}
