package com.and.music.mapper;

import com.and.music.domain.Songs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author and
* @description 针对表【songs】的数据库操作Mapper
* @createDate 2024-10-14 15:31:31
* @Entity .domain.Songs
*/
@Mapper
public interface SongsMapper extends BaseMapper<Songs> {

}




