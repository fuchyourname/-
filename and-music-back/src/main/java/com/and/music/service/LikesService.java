package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Likes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【likes】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface LikesService extends IService<Likes> {
    // 点赞歌曲
    public R removeSong(Integer songId, Integer userId);
    // 取消点赞歌曲
    public R addSong(Integer songId, Integer userId);
    // 点赞动态
    public R removeDynamic(Integer dynamicId, Integer userId);
    // 取消点赞动态
    public R addDynamic(Integer dynamicId, Integer userId);
}
