package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Follow;
import com.and.music.mapper.FollowMapper;
import com.and.music.service.FollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Override
    public R addFollow(Integer userId, Integer followUserId) {
        return null;
    }

    @Override
    public R removeFollow(Integer userId, Integer followUserId) {
        return null;
    }

    @Override
    public R getFollowList(Integer userId) {
        return null;
    }

    @Override
    public R addSinger(Integer userId, Integer singerId) {
        return null;
    }

    @Override
    public R removeSinger(Integer userId, Integer singerId) {
        return null;
    }

    @Override
    public R getSingerList(Integer userId) {
        return null;
    }
}
