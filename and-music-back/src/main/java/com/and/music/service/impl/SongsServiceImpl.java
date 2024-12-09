package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.Albums;
import com.and.music.domain.Artists;
import com.and.music.domain.Songs;
import com.and.music.domain.UserSongs;
import com.and.music.dto.FileDto;
import com.and.music.dto.SongDto;
import com.and.music.dto.UserSongDto;
import com.and.music.mapper.AlbumsMapper;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.mapper.SongsMapper;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.SongsService;
import com.and.music.service.UsersService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author and
 * @description 针对表【songs】的数据库操作Service实现
 * @createDate 2024-10-14 15:31:31
 */
@Service
public class SongsServiceImpl extends ServiceImpl<SongsMapper, Songs>
        implements SongsService {

    @Resource
    private MinioUtils minioUtils;
    @Resource
    private ArtistsMapper artistsMapper;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private MinioProperties minioAutoProperties;
    @Resource
    private RedisTemplate<String, Songs> redisTemplate;

    @Override
    public List<Songs> calculateHotSongs() {
        // 热歌榜根据播放次数排序
        LambdaQueryWrapper<Songs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Songs::getPlayCount).last("LIMIT 10");
        return list(queryWrapper);
    }

    @Override
    public List<Songs> calculateNewSongs() {
        // 新歌榜根据发布时间排序
        LambdaQueryWrapper<Songs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Songs::getCreateTime).last("LIMIT 10");
        return list(queryWrapper);
    }

    @Override
    public void cacheHotSongs(List<Songs> hotSongs) {
        redisTemplate.opsForList().rightPushAll("hot_songs", hotSongs);
    }

    @Override
    public void cacheNewSongs(List<Songs> newSongs) {
        redisTemplate.opsForList().rightPushAll("new_songs", newSongs);
    }

    @Override
    public List<Songs> getHotSongsFromCache() {
        return redisTemplate.opsForList().range("hot_songs", 0, -1);
    }

    @Override
    public List<Songs> getNewSongsFromCache() {
        return redisTemplate.opsForList().range("new_songs", 0, -1);
    }

    @Override
    public R addPlayCount(Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return R.fail("参数错误");
        }
        redisTemplate.opsForHash().increment("SongPlayCount", songId.toString(), 1);
        return R.ok();
    }

    @Override
    public void savePlayCount(Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return;
        }
        Songs song = getById(songId);
        if (ObjectUtil.isNotEmpty(song)) {
            // 在原有的基础上新增redis中的值
            song.setPlayCount(song.getPlayCount() +
                    Long.parseLong(redisTemplate.opsForHash().get("SongPlayCount", songId.toString()).toString()));
            this.baseMapper.updateById(song);
        }
    }

    @Override
    @Transactional
    public R upload(FileDto fileDto) {

        // 插入歌曲信息到数据库
        Songs songs = new Songs();
        songs.setTitle(fileDto.getTitle());
        songs.setArtistId(fileDto.getArtistId());
        songs.setAlbumId(fileDto.getAlbumId());
        songs.setGenreId(fileDto.getGenreId());
        this.baseMapper.insert(songs);

        Integer songId = songs.getSongId();

        try {
            if (fileDto.getMusicFile() != null) {
                // 上传歌曲文件
                String objectPath = PathUtils.getMusicPath(fileDto, songId);

                String musicUrl = minioUtils.putObject(minioAutoProperties.getBucket(),
                        objectPath, fileDto.getMusicFile());
                songs.setFilePath(musicUrl);

                // 获取音乐文件的时长
                String duration = getMusicDuration(fileDto.getMusicFile().getOriginalFilename(), fileDto.getMusicFile().getBytes());
                songs.setDuration(duration);
            }

            if (fileDto.getMusicPic() != null) {
                // 上传歌曲图片
                String objectPath = PathUtils.getCoverPath(fileDto, songId);

                String coverUrl = minioUtils.putObject(minioAutoProperties.getBucket(),
                        objectPath, fileDto.getMusicPic());
                songs.setCoverPath(coverUrl);
            }

            if (fileDto.getLyricFile() != null) {
                // 上传歌词文件
                String objectPath = PathUtils.getLyricPath(fileDto, songId);

                String lyricUrl = minioUtils.putObject(minioAutoProperties.getBucket(),
                        objectPath, fileDto.getLyricFile());
                songs.setLyricPath(lyricUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
        this.baseMapper.updateById(songs);
        return R.ok();
    }

    @Override
    public R getSongDetail(Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return R.fail("参数错误");
        }

        SongVo songVo = new SongVo();

        Songs songs = getById(songId);

        if (ObjectUtil.isEmpty(songs)) {
            return R.fail("未找到歌曲");
        }

        songVo.setSongId(songId)
                .setTitle(songs.getTitle())
                .setCoverPath(songs.getCoverPath())
                .setFilePath(songs.getFilePath())
                .setLyricPath(songs.getLyricPath())
                .setDuration(songs.getDuration()); // 添加时长到SongVo

        if (ObjectUtil.isNotEmpty(songs.getArtistId())) {
            Artists artists = artistsMapper.selectById(songs.getArtistId());
            if (ObjectUtil.isNotEmpty(artists)) {
                songVo.setArtist(artists.getName());
            }
        }
        if (ObjectUtil.isNotEmpty(songs.getAlbumId())) {
            Albums albums = albumsMapper.selectById(songs.getAlbumId());
            if (ObjectUtil.isNotEmpty(albums)) {
                songVo.setAlbum(albums.getTitle());
            }
        }
        return R.ok(songVo);
    }

    @Override
    public R uploadSong(UserSongDto userSongDto) {

        if (ObjectUtil.isEmpty(userSongDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(userSongDto.getSong())) {
            return R.fail("文件不能为空");
        }
        // 获取文件的类型、大小、文件名
        return null;
    }

    @Override
    public R getSongByName(String songName) {
        return null;
    }

    /**
     * 获取音乐文件的时长
     *
     * @param fileName  文件名
     * @param fileBytes 文件字节数组
     * @return 音乐文件的时长（秒）
     */
    private String getMusicDuration(String fileName, byte[] fileBytes) {
        // 创建临时文件
        java.io.File tempFile = null;
        try {
            tempFile = java.io.File.createTempFile("temp", fileName.substring(fileName.lastIndexOf(".")));
            java.nio.file.Files.write(tempFile.toPath(), fileBytes);

            CommandLine cmdLine = CommandLine.parse("ffmpeg -i " + tempFile.getAbsolutePath() + " -f null -");
            DefaultExecutor executor = new DefaultExecutor();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            executor.setStreamHandler(streamHandler);

            int exitValue = executor.execute(cmdLine);
            if (exitValue == 0) {
                String output = outputStream.toString();
                String[] lines = output.split("\n");
                for (String line : lines) {
                    if (line.contains("Duration")) {
                        String durationStr = line.split("Duration: ")[1].split(",")[0];
                        String[] parts = durationStr.split(":");
                        int hours = Integer.parseInt(parts[0]);
                        int minutes = Integer.parseInt(parts[1]);
                        double seconds = Double.parseDouble(parts[2].replace(",", "."));

                        // 格式化时长为 mm:ss 或 HH:mm:ss
                        if (hours > 0) {
                            return String.format("%02d:%02d:%02d", hours, minutes, (int) seconds);
                        } else {
                            return String.format("%02d:%02d", minutes, (int) seconds);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error executing FFmpeg command", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete(); // 删除临时文件
            }
        }

        return "-1"; // 返回"-1"表示获取时长失败
    }

}
