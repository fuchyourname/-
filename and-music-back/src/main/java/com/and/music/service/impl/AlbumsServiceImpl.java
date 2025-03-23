package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.Albums;
import com.and.music.domain.Artists;
import com.and.music.domain.Genres;
import com.and.music.domain.Playlists;
import com.and.music.dto.AlbumDto;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.mapper.AlbumsMapper;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.mapper.GenresMapper;
import com.and.music.mapper.SongsMapper;
import com.and.music.service.AlbumsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.AlbumVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ArtistsMapper artistsMapper;
    @Resource
    private GenresMapper genresMapper;
    @Resource
    private MinioUtils minioUtils;
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private RedisTemplate<String, Object> stringRedisTemplate;

    @Override
    public R getAlbumsByName(String name) {

        List<Albums> albumsList =
                list(new LambdaQueryWrapper<Albums>().eq(Albums::getTitle, name));
        return R.ok(albumsList);
    }

    @Override
    public R getArtistAlbumsPage(PageInfo pageInfo) {
        return null;
    }

    @Override
    @Transactional
    public R addAlbum(AlbumDto albumDto) {

        Albums albums = new Albums();
        albums.setTitle(albumDto.getTitle())
                .setArtistId(albumDto.getArtistId())
                .setDescription(albumDto.getDescription())
                .setType(albumDto.getType())
                .setStatus(0)
                .setCreateUser(UserContext.getUser().getUserId())
                .setPlayCount(0L)
                .setUpdateUser(UserContext.getUser().getUserId());

        save(albums);

        if (ObjectUtil.isNotEmpty(albumDto.getCoverImage())) {
            String coverImageUrl = "";
            try {
                FileDto file = new FileDto();
                file.setMusicPic(albumDto.getCoverImage());
                file.setGenreId(4);
                String objectPath = PathUtils.getCoverPath(albums.getAlbumId(), file);

              coverImageUrl = minioUtils.putObject(minioProperties.getBucket(),
                        objectPath, albumDto.getCoverImage());

              albums.setCoverImage(coverImageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        updateById(albums);
        return R.ok();
    }

    @Override
    public R updateAlbum(AlbumDto albumDto) {

        if (ObjectUtil.isEmpty(albumDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(albumDto.getAlbumId())) {
            return R.fail("参数错误");
        }
        Albums albums = getById(albumDto.getAlbumId());
        albums.setTitle(albumDto.getTitle());
        if (ObjectUtil.isNotEmpty(albumDto.getArtistId())) {
            albums.setArtistId(albumDto.getArtistId());
        }
        albums.setDescription(albumDto.getDescription());
        albums.setCreateTime(albums.getCreateTime());
        albums.setType(albumDto.getType());
        albums.setUpdateUser(UserContext.getUser().getUserId());

        if (ObjectUtil.isNotEmpty(albumDto.getCoverImage())) {
            try {
                // http://192.168.154.1:9000/music/cover/1/27_1732354594609.jpg
                String objectName = albums.getCoverImage().split("/")[3];
                minioUtils.removeObject(minioProperties.getBucket(), objectName);
                MultipartFile pl = albumDto.getCoverImage();
                if (pl != null) {
                    // 上传歌曲图片
                    FileDto fileDto = new FileDto();
                    fileDto.setMusicPic(pl);
                    fileDto.setGenreId(4);
                    String objectPath = PathUtils.getCoverPath(fileDto, albumDto.getAlbumId());

                    String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                            objectPath, fileDto.getMusicPic());
                    albums.setCoverImage(coverUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("上传失败");
            }
        }
        this.baseMapper.updateById(albums);
        return R.ok(albums);
    }

    @Override
    public R getAlbumPage(PageInfo pageInfo) {

        LambdaQueryWrapper<Albums> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(pageInfo.getName()), Albums::getTitle, pageInfo.getName())
                .eq(ObjectUtil.isNotEmpty(pageInfo.getType()), Albums::getType, pageInfo.getType())
                .orderByDesc(Albums::getPlayCount);

        if (ObjectUtil.isNotEmpty(pageInfo.getStartTime()) ||  ObjectUtil.isNotEmpty(pageInfo.getEndTime())) {
            if (ObjectUtil.isEmpty(pageInfo.getStartTime())) {
                queryWrapper.le(Albums::getCreateTime, pageInfo.getEndTime());
            } else if (ObjectUtil.isEmpty(pageInfo.getEndTime())) {
                queryWrapper.ge(Albums::getCreateTime, pageInfo.getStartTime());
            } else {
                queryWrapper.between(Albums::getCreateTime, pageInfo.getStartTime(), pageInfo.getEndTime());
            }
        }
        Page<Albums> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page(page, queryWrapper);
        List<Albums> albumsList = page.getRecords();
        if (ObjectUtil.isEmpty(albumsList)) {
            return R.ok(albumsList);
        }
        List<Integer> artistIdList = albumsList.stream().map(Albums::getArtistId).collect(Collectors.toList());
        List<Integer> genreIdList = albumsList.stream().map(Albums::getType).collect(Collectors.toList());
        List<Artists> artistsList = artistsMapper.selectBatchIds(artistIdList);
        List<Genres> genresList = genresMapper.selectBatchIds(genreIdList);
        Map<Integer, Artists> artistMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, v -> v));
        Map<Integer, Genres> genreMap = genresList.stream().collect(Collectors.toMap(Genres::getGenreId, v -> v));
        List<AlbumVo> albumVoList = albumsList.stream().map(album -> {
            return new AlbumVo()
                    .setAlbumId(album.getAlbumId())
                    .setTitle(album.getTitle())
                    .setArtist(artistMap.get(album.getArtistId()).getName())
                    .setCoverImage(album.getCoverImage())
                    .setDescription(album.getDescription())
                    .setType(genreMap.get(album.getType()).getName())
                    .setCreateTime(album.getCreateTime());
        }).collect(Collectors.toList());
        return R.ok(albumVoList, page.getTotal());
    }

    @Override
    public R getAlbumDetail(Integer albumId) {

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
            stringRedisTemplate.opsForHash().delete("AlbumPlayCount", albumId.toString());
        }
    }
}




