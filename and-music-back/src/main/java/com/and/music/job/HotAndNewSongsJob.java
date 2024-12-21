package com.and.music.job;

import com.and.music.domain.Songs;
import com.and.music.service.SongsService;
import com.and.music.vo.SongVo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class HotAndNewSongsJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HotAndNewSongsJob.class);

    @Resource
    private SongsService songsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始计算热歌榜和新歌榜...");

        // 计算热歌榜
        List<SongVo> hotSongs = songsService.calculateHotSongs();
        logger.info("热歌榜计算完成，共 {} 首歌曲", hotSongs.size());

        // 计算新歌榜
        List<SongVo> newSongs = songsService.calculateNewSongs();
        logger.info("新歌榜计算完成，共 {} 首歌曲", newSongs.size());

        // 将结果存储或缓存
        songsService.cacheHotSongs(hotSongs);
        songsService.cacheNewSongs(newSongs);

        logger.info("热歌榜和新歌榜计算并缓存完成");
    }
}

