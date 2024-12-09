package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Songs;
import com.and.music.domain.UserSongs;
import com.and.music.dto.FileDto;
import com.and.music.dto.SongDto;
import com.and.music.dto.UserSongDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author and
* @description 针对表【songs】的数据库操作Service
* @createDate 2024-10-14 15:31:31
*/
public interface SongsService extends IService<Songs> {

    // 上传歌曲
    R upload(FileDto fileDto);

    // 获取歌曲详情
    R getSongDetail(Integer songId);

    // 用户上传歌曲
    R uploadSong(UserSongDto userSongDto);

    // 根据歌曲名进行模糊查询
    R getSongByName(String songName);

    List<Songs> calculateHotSongs();

    List<Songs> calculateNewSongs();

    void cacheHotSongs(List<Songs> hotSongs);

    void cacheNewSongs(List<Songs> newSongs);

    List<Songs> getHotSongsFromCache();

    List<Songs> getNewSongsFromCache();

    // 增加歌单的播放次数
    R addPlayCount(Integer songId);

    // 保存歌单的播放次数
    void savePlayCount(Integer songId);
}
