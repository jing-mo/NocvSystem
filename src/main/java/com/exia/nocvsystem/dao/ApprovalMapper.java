package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.ApprovalProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ApprovalMapper extends BaseMapper<ApprovalProcess> {
    @Update("ALTER TABLE approval_process AUTO_INCREMENT = 0;")
    void autoIncrement();
}
