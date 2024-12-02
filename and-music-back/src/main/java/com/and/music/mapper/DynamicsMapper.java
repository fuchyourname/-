package com.and.music.mapper;

import com.and.music.domain.Dynamics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicsMapper extends BaseMapper<Dynamics> {
}
