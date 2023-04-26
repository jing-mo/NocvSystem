package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.entity.NocvNews;

import java.util.List;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/16 17:55
 */

public interface NocvNewsService extends IService<NocvNews> {
    List<NocvNews> listNewsLimit5();
}
