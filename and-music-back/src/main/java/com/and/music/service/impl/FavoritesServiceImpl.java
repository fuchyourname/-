package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Favorites;
import com.and.music.mapper.FavoritesMapper;
import com.and.music.service.FavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【favorites】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites>
    implements FavoritesService {
    @Override
    public R addFavorites(Integer playlistId, Integer userId) {
        return null;
    }

    @Override
    public R removeFavorites(Integer playlistId, Integer userId) {
        return null;
    }

    @Override
    public R addSong(Integer songId, Integer userId) {
        return null;
    }

    @Override
    public R removeSong(Integer songId, Integer userId) {
        return null;
    }
}




