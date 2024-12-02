package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Songs;
import com.and.music.dto.FileDto;
import com.and.music.dto.SongDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【songs】的数据库操作Service
* @createDate 2024-10-14 15:31:31
*/
public interface SongsService extends IService<Songs> {

    // 上传歌曲
    public R upload(FileDto fileDto);

    // 获取歌曲详情
    public R getSongDetail(Integer songId);

    // 用户上传歌曲
    public R uploadSong(SongDto songDto);
}
