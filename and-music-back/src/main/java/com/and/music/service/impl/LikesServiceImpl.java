package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Likes;
import com.and.music.mapper.LikesMapper;
import com.and.music.service.LikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【likes】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService {

    @Override
    public R removeSong(Integer songId, Integer userId) {
        return null;
    }

    @Override
    public R addSong(Integer songId, Integer userId) {
        return null;
    }

    @Override
    public R removeDynamic(Integer dynamicId, Integer userId) {
        return null;
    }

    @Override
    public R addDynamic(Integer dynamicId, Integer userId) {
        return null;
    }
}




