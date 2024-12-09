package com.and.music.job;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.service.AlbumsService;
import com.and.music.service.PlaylistsService;
import com.and.music.service.SongsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
public class AddPlayCountJob implements Job {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private SongsService songsService;
    @Resource
    private AlbumsService albumsService;
    @Resource
    private PlaylistsService playlistsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*
         1. 取出redis中歌曲、专辑、歌单的播放次数
         2. 更新数据库中歌曲、专辑、歌单的播放次数
         3. 清空redis中歌曲、专辑、歌单的播放次数
         */
        log.info("开始处理播放次数");
        Map<Object, Object> songPlayCount = redisTemplate.opsForHash().entries("SongPlayCount");
        if (ObjectUtil.isNotEmpty(songPlayCount)) {
            for (Map.Entry<Object, Object> entry : songPlayCount.entrySet()) {
                songsService.savePlayCount(Integer.parseInt(entry.getKey().toString()));
            }
            // 检查哈希表是否存在且不为空
            if (redisTemplate.hasKey("SongPlayCount")) {
                redisTemplate.delete("SongPlayCount");
            }
        }

        Map<Object, Object> albumPlayCount = redisTemplate.opsForHash().entries("AlbumPlayCount");
        if (ObjectUtil.isNotEmpty(albumPlayCount)) {
            for (Map.Entry<Object, Object> entry : albumPlayCount.entrySet()) {
                albumsService.savePlayCount(Integer.parseInt(entry.getKey().toString()));
            }
            // 检查哈希表是否存在且不为空
            if (redisTemplate.hasKey("AlbumPlayCount")) {
                redisTemplate.delete("AlbumPlayCount");
            }
        }

        Map<Object, Object> playlistPlayCount = redisTemplate.opsForHash().entries("PlayListPlayCount");
        if (ObjectUtil.isNotEmpty(playlistPlayCount)) {
            for (Map.Entry<Object, Object> entry : playlistPlayCount.entrySet()) {
                playlistsService.savePlayCount(Integer.parseInt(entry.getKey().toString()));
            }
            // 检查哈希表是否存在且不为空
            if (redisTemplate.hasKey("PlaylistPlayCount")) {
                redisTemplate.delete("PlaylistPlayCount");
            }
        }
        log.info("播放次数处理完毕");
    }
}
