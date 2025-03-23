package com.and.music.mapper;

import com.and.music.domain.Label;
import com.and.music.domain.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {
}
