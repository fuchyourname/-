package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Favorites;
import com.and.music.mapper.FavoritesMapper;
import com.and.music.service.FavoritesService;
import com.and.music.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【favorites】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites>
    implements FavoritesService {

    @Override
    public R addFavorites(Integer playlistId) {

        if (ObjectUtil.isEmpty(playlistId)) {
            return R.fail("参数错误");
        }
        Favorites favorites = new Favorites()
                .setContentId(playlistId)
                .setType(2)
                .setUserId(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(favorites) > 0) {
            return R.ok();
        }
        return R.fail("收藏失败");
    }

    @Override
    public R removeFavorites(Integer playlistId) {

        if (ObjectUtil.isEmpty(playlistId)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Favorites> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorites::getContentId, playlistId)
                .eq(Favorites::getType, 2)
                .eq(Favorites::getUserId, UserContext.getUser().getUserId());

        if (this.baseMapper.delete(wrapper) > 0) {
            return R.ok();
        }
        return R.fail("取消收藏失败");
    }
}




