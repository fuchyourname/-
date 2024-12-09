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
    // 获取推荐歌单
    public R getRecommendPlaylists();
    // 获取歌单详情
    public R getPlaylistDetail(Integer playlistId);
    // 根据类型获取歌单列表
    public R getPlaylistsByType(Integer type);
    // 获取用户创建的歌单列表
    public R getUserPlaylists();
    // 获取用户收藏的歌单列表
    public R getUserFavorites();
    // 根据歌单名进行模糊查询
    public R getPlaylistsByName(String playlistName);
    // 增加歌单的播放次数
    R addPlayCount(Integer playlistId);
    // 保存歌单的播放次数
    void savePlayCount(Integer playlistId);
}
