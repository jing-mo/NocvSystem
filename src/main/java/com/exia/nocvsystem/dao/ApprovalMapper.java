package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.ApprovalProcess;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalMapper extends BaseMapper<ApprovalProcess> {
}
