package com.and.music.mapper;

import com.and.music.domain.Playlists;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author and
* @description 针对表【playlists】的数据库操作Mapper
* @createDate 2024-10-14 15:31:30
* @Entity .domain.Playlists
*/
@Mapper
public interface PlaylistsMapper extends BaseMapper<Playlists> {

}




