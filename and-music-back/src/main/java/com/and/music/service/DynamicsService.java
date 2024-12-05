package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Dynamics;
import com.and.music.domain.Users;
import com.and.music.dto.DynamicsDto;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DynamicsService extends IService<Dynamics> {

    // 添加动态
    public R addDynamics(DynamicsDto dynamicsDto);

    // 获取动态列表
    public R getDynamicsList();
}
