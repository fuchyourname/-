package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Playlists;
import com.and.music.dto.PlaylistDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【playlists】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface PlaylistsService extends IService<Playlists> {

    // 添加歌单
    public R addPlaylists(PlaylistDto playlistDto);
    // 获取歌单下的歌曲
    public R getSongs(Integer playlistId);
    // 获取推荐歌单
    public R getRecommendPlaylists();
    // 获取歌单详情
    public R getPlaylistDetail(Integer playlistId);
    // 根据类型获取歌单列表
    public R getPlaylistsByType(Integer type);
}
