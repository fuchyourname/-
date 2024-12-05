package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Favorites;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【favorites】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface FavoritesService extends IService<Favorites> {

    // 收藏歌单
    public R addFavorites(Integer playlistId);

    // 取消收藏歌单
    public R removeFavorites(Integer playlistId);
}
