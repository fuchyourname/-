package com.and.music.service;

import cn.hutool.db.Page;
import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.dto.ArtistDto;
import com.and.music.dto.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【artists】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface ArtistsService extends IService<Artists> {

    // 根据名称
    public R getArtistsByName(String name);

    // 获取歌手的所有歌曲
    public R getSongs(Integer artistId);
    // 获取歌手的所有专辑
    public R getAlbums(Integer artistId);
    // 添加歌手
    public R addArtist(ArtistDto artistDto);
    // 更新歌手信息
    public R updateArtist(ArtistDto artistDto);
    // 分页
    public R getArtistPage(PageInfo pageInfo);

    // 根据ID查询歌手
    public R getArtistById(Integer artistId);

    // 根据查询条件获取歌手列表
    public R getArtists(ArtistDto artistDto);
}
