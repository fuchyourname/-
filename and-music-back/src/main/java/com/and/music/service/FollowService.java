package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FollowService extends IService<Follow> {

    // 关注好友
    public R addFollow(Integer followUserId);

    // 取消关注
    public R removeFollow(Integer followUserId);

    // 获取关注列表
    public R getFollowList();

    // 关注歌手
    public R addSinger(Integer singerId);

    // 取消关注歌手
    public R removeSinger(Integer singerId);

    // 获取关注歌手列表
    public R getSingerList();
}
