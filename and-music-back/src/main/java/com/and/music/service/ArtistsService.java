package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.dto.ArtistDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【artists】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface ArtistsService extends IService<Artists> {

    // 添加歌手
    public R addArtist(ArtistDto artistDto);

    // 根据ID查询歌手
    public R getArtistById(Integer artistId);

    // 根据查询条件获取歌手列表
    public R getArtists(ArtistDto artistDto);

    // 根据歌手名称查询歌手
    public R getArtistByName(String artistName);
}
