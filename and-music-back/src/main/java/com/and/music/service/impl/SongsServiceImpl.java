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
    private ArtistsMapper artistsMapper;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private MinioProperties minioAutoProperties;

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
                .setLyricPath(songs.getLyricPath());

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
}




