package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.ApprovalMapper;
import com.exia.nocvsystem.entity.ApprovalProcess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/27 20:39
 */
@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, ApprovalProcess> implements ApprovalService
{
    @Autowired
    ApprovalMapper approvalMapper;
    @Override
    public void autoIncrement() {
        approvalMapper.autoIncrement();
    }
}
