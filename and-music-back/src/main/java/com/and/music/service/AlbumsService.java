package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【albums】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface AlbumsService extends IService<Albums> {

    // 新增专辑
    R addAlbum(AlbumDto albumDto);
    // 获取专辑列表
    R getAlbumList(Integer userId);
    // 获取专辑详情
    R getAlbumDetail(Integer albumId);
}
