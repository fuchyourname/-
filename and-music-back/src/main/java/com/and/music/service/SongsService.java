package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Songs;
import com.and.music.domain.UserSongs;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.dto.SongDto;
import com.and.music.dto.UserSongDto;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author and
* @description 针对表【songs】的数据库操作Service
* @createDate 2024-10-14 15:31:31
*/
public interface SongsService extends IService<Songs> {

    // 分页获取歌手的所有歌曲
    public R getArtistSongsPage(PageInfo pageInfo);

    // 上传歌曲
    R upload(FileDto fileDto);

    // 获取歌曲详情
    R getSongDetail(Integer songId);

    // 用户上传歌曲
    R uploadSong(UserSongDto userSongDto);

    List<SongVo> calculateHotSongs();

    List<SongVo> calculateNewSongs();

    void cacheHotSongs(List<SongVo> hotSongs);

    void cacheNewSongs(List<SongVo> newSongs);

    List<SongVo> getHotSongsFromCache();

    List<SongVo> getNewSongsFromCache();

    // 增加歌单的播放次数
    R addPlayCount(Integer songId);

    // 保存歌单的播放次数
    void savePlayCount(Integer songId);
    // 修改歌曲信息
    R updateSong(SongDto songDto);
    // 分页
    R getSongPage(PageInfo pageInfo);
}
