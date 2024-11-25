package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Songs;
import com.and.music.dto.FileDto;
import com.and.music.dto.SongDto;
import com.and.music.mapper.SongsMapper;
import com.and.music.service.SongsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    private PathUtils pathUtils;

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
                String bucketName = "music";
                String objectPath = "file/" + songId + ".mp3";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String musicUrl = minioUtils.putObject(bucketName, objectPath, fileDto.getMusicFile());
                songs.setFilePath(musicUrl);
            }

            if (fileDto.getMusicPic() != null) {
                // 上传歌曲图片
                String bucketName = "music";
                String objectPath = "cover/" + songs.getGenreId() + "/" + songId + ".jpg";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String coverUrl = minioUtils.putObject(bucketName, objectPath, fileDto.getMusicPic());
                songs.setCoverPath(coverUrl);
            }

            if (fileDto.getLyricFile() != null) {
                // 上传歌词文件
                String bucketName = "music";
                String objectPath = "lyric/" + songId + ".lyric";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String lyricUrl = minioUtils.putObject(bucketName, objectPath, fileDto.getLyricFile());
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
    public R addSong(SongDto songDto) {
        return null;
    }
}




