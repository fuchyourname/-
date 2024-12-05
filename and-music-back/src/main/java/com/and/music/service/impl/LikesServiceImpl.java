package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Likes;
import com.and.music.domain.Songs;
import com.and.music.mapper.LikesMapper;
import com.and.music.service.LikesService;
import com.and.music.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public R removeSong(Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Likes::getContentId, songId)
                .eq(Likes::getType, 1)
                .eq(Likes::getUserId, UserContext.getUser().getUserId());

        if (this.baseMapper.delete(queryWrapper) > 0) {
            return R.ok();
        }
        return R.fail("取消点赞歌曲失败");
    }

    @Override
    public R addSong(Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return R.fail("参数错误");
        }

        Likes likes = new Likes();
        likes.setContentId(songId)
                .setType(1)
                .setUserId(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(likes) > 0) {
            return R.ok();
        }
        return R.fail("点赞歌曲失败");
    }

    @Override
    public R removeDynamic(Integer dynamicId) {

        if (ObjectUtil.isEmpty(dynamicId)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Likes::getContentId, dynamicId)
                .eq(Likes::getType, 3)
                .eq(Likes::getUserId, UserContext.getUser().getUserId());

        if (this.baseMapper.delete(queryWrapper) > 0) {
            return R.ok();
        }
        return R.fail("取消点赞动态失败");
    }

    @Override
    public R addDynamic(Integer dynamicId) {

        if (ObjectUtil.isEmpty(dynamicId)) {
            return R.fail("参数错误");
        }

        Likes likes = new Likes();
        likes.setContentId(dynamicId)
                .setType(3)
                .setUserId(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(likes) > 0) {
            return R.ok();
        }
        return R.fail("点赞动态失败");
    }
}




