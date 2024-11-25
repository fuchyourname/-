package com.and.music.mapper;

import com.and.music.domain.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author and
* @description 针对表【likes】的数据库操作Mapper
* @createDate 2024-10-14 15:31:30
* @Entity .domain.Likes
*/
@Mapper
public interface LikesMapper extends BaseMapper<Likes> {

}




