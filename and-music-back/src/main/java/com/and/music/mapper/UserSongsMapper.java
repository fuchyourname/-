package com.and.music.mapper;

import com.and.music.domain.Albums;
import com.and.music.domain.UserSongs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSongsMapper extends BaseMapper<UserSongs> {
}
