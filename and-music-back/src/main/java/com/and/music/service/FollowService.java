package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FollowService extends IService<Follow> {

    // 关注好友
    public R addFollow(Integer userId, Integer followUserId);

    // 取消关注
    public R removeFollow(Integer userId, Integer followUserId);

    // 获取关注列表
    public R getFollowList(Integer userId);

    // 关注歌手
    public R addSinger(Integer userId, Integer singerId);

    // 取消关注歌手
    public R removeSinger(Integer userId, Integer singerId);

    // 获取关注歌手列表
    public R getSingerList(Integer userId);
}
