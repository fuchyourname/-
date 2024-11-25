package com.and.music.mapper;

import com.and.music.domain.PlaylistSongs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author and
* @description 针对表【playlist_songs】的数据库操作Mapper
* @createDate 2024-10-14 15:31:30
* @Entity .domain.PlaylistSongs
*/
@Mapper
public interface PlaylistSongsMapper extends BaseMapper<PlaylistSongs> {

    // 批量插入歌单歌曲
    void insertBatch(Integer playlistId, List<Integer> songIds);
}




