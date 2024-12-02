package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Dynamics;
import com.and.music.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DynamicsService extends IService<Dynamics> {

    // 添加动态
    public R addDynamics(Dynamics dynamics);

    // 获取动态
    public R getDynamics(Integer userId);

    // 获取动态详情
    public R getDynamicsDetail(Integer dynamicId);

    // 获取动态列表
    public R getDynamicsList(Integer userId);
}
