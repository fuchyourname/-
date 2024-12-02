package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Comments;
import com.and.music.domain.Dynamics;
import com.and.music.mapper.CommentsMapper;
import com.and.music.mapper.DynamicsMapper;
import com.and.music.service.CommentsService;
import com.and.music.service.DynamicsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DynamicsServiceImpl extends ServiceImpl<DynamicsMapper, Dynamics>
        implements DynamicsService {
    @Override
    public R addDynamics(Dynamics dynamics) {
        return null;
    }

    @Override
    public R getDynamics(Integer userId) {
        return null;
    }

    @Override
    public R getDynamicsDetail(Integer dynamicId) {
        return null;
    }

    @Override
    public R getDynamicsList(Integer userId) {
        return null;
    }
}
