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

    public R upload(FileDto fileDto);

    // 新增歌曲
    public R addSong(SongDto songDto);
}
