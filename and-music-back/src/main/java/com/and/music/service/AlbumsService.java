package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.and.music.dto.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【albums】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface AlbumsService extends IService<Albums> {

    // 根据名称查询专辑
    public R getAlbumsByName(String name);
    // 分页获取歌手的所有专辑
    public R getArtistAlbumsPage(PageInfo pageInfo);

    // 新增专辑
    R addAlbum(AlbumDto albumDto);
    // 修改专辑
    R updateAlbum(AlbumDto albumDto);
    // 分页
    R getAlbumPage(PageInfo pageInfo);
    // 获取专辑详情
    R getAlbumDetail(Integer albumId);
    // 增加专辑播放次数
    R addPlayCount(Integer albumId);
    // 保存专辑播放次数
    void savePlayCount(Integer albumId);
}
