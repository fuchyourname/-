package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.and.music.mapper.AlbumsMapper;
import com.and.music.mapper.SongsMapper;
import com.and.music.service.AlbumsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author and
* @description 针对表【albums】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class AlbumsServiceImpl extends ServiceImpl<AlbumsMapper, Albums>
    implements AlbumsService {

    @Resource
    private SongsMapper songsMapper;
    @Resource
    private RedisTemplate<String, Object> stringRedisTemplate;
    @Override
    public R addAlbum(AlbumDto albumDto) {
        return null;
    }

    @Override
    public R getAlbumList(Integer userId) {
        return null;
    }

    @Override
    public R getAlbumDetail(Integer albumId) {

        return null;
    }
    @Override
    public R getAlbumByName(String albumName) {
        return null;
    }

    @Override
    public R addPlayCount(Integer albumId) {

        if (ObjectUtil.isEmpty(albumId)) {
            return R.fail("参数错误");
        }
        stringRedisTemplate.opsForHash().increment("AlbumPlayCount", albumId.toString(), 1);
        return R.ok();
    }

    @Override
    public void savePlayCount(Integer albumId) {

        if (ObjectUtil.isEmpty(albumId)) {
            return;
        }

        Albums album = getById(albumId);
        if (ObjectUtil.isNotEmpty(album)) {
            // 在原有的基础上新增redis中的值
            album.setPlayCount(album.getPlayCount() +
                    Long.parseLong(stringRedisTemplate.opsForHash().get("AlbumPlayCount", albumId.toString()).toString()));
            this.baseMapper.updateById(album);
        }
    }
}




