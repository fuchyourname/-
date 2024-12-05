package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.and.music.mapper.AlbumsMapper;
import com.and.music.mapper.SongsMapper;
import com.and.music.service.AlbumsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author and
* @description 针对表【albums】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class AlbumsServiceImpl extends ServiceImpl<AlbumsMapper, Albums>
    implements AlbumsService {

    @Resource
    private SongsMapper songsMapper;
    @Override
    public R addAlbum(AlbumDto albumDto) {
        return null;
    }

    @Override
    public R getAlbumList(Integer userId) {
        return null;
    }

    @Override
    public R getAlbumDetail(Integer albumId) {

        return null;
    }
}




