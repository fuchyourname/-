package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.CommonConstants;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.*;
import com.and.music.dto.ArtistDto;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.mapper.*;
import com.and.music.service.ArtistsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.PinyinUtil;
import com.and.music.utils.UserContext;
import com.and.music.vo.AlbumVo;
import com.and.music.vo.SingerVo;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author and
* @description 针对表【artists】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class ArtistsServiceImpl extends ServiceImpl<ArtistsMapper, Artists>
    implements ArtistsService {

    @Resource
    private MinioUtils minioUtils;
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private SongsMapper songsMapper;
    @Resource
    private GenresMapper genresMapper;
    @Resource
    private FollowMapper followMapper;

    @Override
    public R getArtistsByName(String name) {

        if (ObjectUtil.isEmpty(name)) {
            return R.fail("参数错误");
        }

        List<Artists> artistsList = list(new QueryWrapper<Artists>().like("name", name));
        return R.ok(artistsList);
    }

    @Override
    public R getSongs(Integer artistId) {

        if (ObjectUtil.isEmpty(artistId)) {
            return R.fail("参数错误");
        }
        LambdaQueryWrapper<Songs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Songs::getArtistId, artistId);
        List<Songs> songsList = songsMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(songsList)) {
            return R.ok(new ArrayList<>());
        }
        List<Integer> artistIds = songsList.stream().map(Songs::getArtistId).collect(Collectors.toList());
        List<Artists> artistsList = this.baseMapper.selectBatchIds(artistIds);
        Map<Integer, Artists> artistMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, v -> v));
        List<Integer> albumIds = songsList.stream().map(Songs::getAlbumId).collect(Collectors.toList());
        List<Albums> albumsList = albumsMapper.selectBatchIds(albumIds);
        Map<Integer, Albums> albumMap = albumsList.stream().collect(Collectors.toMap(Albums::getAlbumId, v -> v));
        List<SongVo> songsListVo = songsList.stream().map(song -> {
            return new SongVo()
                    .setSongId(song.getSongId())
                    .setTitle(song.getTitle())
                    .setArtist(artistMap.get(song.getArtistId()).getName())
                    .setAlbum(albumMap.get(song.getAlbumId()).getTitle())
                    .setGenre(genresMapper.selectById(song.getGenreId()).getName())
                    .setDuration(song.getDuration())
                    .setCoverPath(song.getCoverPath());
        }).collect(Collectors.toList());
        return R.ok(songsListVo);
    }

    @Override
    public R getAlbums(Integer artistId) {

        if (ObjectUtil.isEmpty(artistId)) {
            return R.fail("参数错误");
        }
        LambdaQueryWrapper<Albums> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Albums::getArtistId, artistId);
        List<Albums> albumsList = albumsMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(albumsList)) {
            return R.ok(new ArrayList<>());
        }
        List<Integer> artistIds = albumsList.stream().map(Albums::getArtistId).collect(Collectors.toList());
        List<Artists> artistsList = this.baseMapper.selectBatchIds(artistIds);
        Map<Integer, Artists> artistMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, v -> v));
        List<AlbumVo> albumsListVo = albumsList.stream().map(album -> {
            return new AlbumVo()
                    .setAlbumId(album.getAlbumId())
                    .setTitle(album.getTitle())
                    .setArtist(artistMap.get(album.getArtistId()).getName())
                    .setCoverImage(album.getCoverImage())
                    .setDescription(album.getDescription())
                    .setSongCount(album.getSongCount())
                    .setPlayCount(album.getPlayCount());
        }).collect(Collectors.toList());
        return R.ok(albumsListVo);
    }

    @Override
    public R addArtist(ArtistDto artistDto) {
        /*
        * 1、判断歌手是否已经存在
        * 2、判断歌手头像是否上传成功
        * 3、保存歌手信息
        * 4、返回结果
         */
        if (ObjectUtil.isEmpty(artistDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(artistDto.getName())) {
            return R.fail("歌手名称不能为空");
        }
        if (ObjectUtil.isEmpty(artistDto.getPic())) {
            return R.fail("歌手头像不能为空");
        }
        List<Artists> artistsList =
                list(new QueryWrapper<Artists>().eq("name", artistDto.getName()));
        if (ObjectUtil.isNotEmpty(artistsList)) {
            return R.fail("歌手名称重复");
        }
        Artists artists = new Artists();
        artists.setName(artistDto.getName());
        artists.setBio(artistDto.getBio());
        artists.setSex(artistDto.getSex());
        artists.setNationality(artistDto.getNationality());
        artists.setCreateUser(1);
        artists.setUpdateUser(1);
        artists.setInitials(PinyinUtil.getFirstLetters(artistDto.getName()));

        if (!save(artists)) {
            return R.fail("添加失败");
        }

        try {
            MultipartFile pl = artistDto.getPic();
            if (pl != null) {
                // 上传歌曲图片
                String bucketName = "music";
                String objectPath = "cover/" + 2 + "/" + artists.getArtistId() + ".jpg";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String coverUrl = minioUtils.putObject(bucketName, objectPath, pl);

                artists.setPicUrl(coverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }

        if (updateById(artists)) {
            return R.ok();
        }
        return R.fail("添加失败");
    }

    @Override
    public R updateArtist(ArtistDto artistDto) {
        if (ObjectUtil.isEmpty(artistDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(artistDto.getArtistId())) {
            return addArtist(artistDto);
        }
        Artists artists = getById(artistDto.getArtistId());
        artists.setName(artistDto.getName());
        artists.setBio(artistDto.getBio());
        artists.setSex(artistDto.getSex());
        artists.setNationality(artistDto.getNationality());
        artists.setUpdateUser(UserContext.getUser().getUserId());
        if (ObjectUtil.isNotEmpty(artistDto.getPic())) {
            try {
                // http://192.168.154.1:9000/music/cover/1/27_1732354594609.jpg
                String objectName = artists.getPicUrl().split("/")[3];
                minioUtils.removeObject(minioProperties.getBucket(), objectName);
                MultipartFile pl = artistDto.getPic();
                if (pl != null) {
                    // 上传歌曲图片
                    FileDto fileDto = new FileDto();
                    fileDto.setMusicPic(pl);
                    fileDto.setGenreId(2);
                    String objectPath = PathUtils.getCoverPath(fileDto, artistDto.getArtistId());

                    String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                            objectPath, fileDto.getMusicPic());
                    artists.setPicUrl(coverUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("上传失败");
            }
        }
        this.baseMapper.updateById(artists);
        return R.ok(artists);
    }

    @Override
    public R getArtistPage(PageInfo pageInfo) {

        LambdaQueryWrapper<Artists> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(pageInfo.getName()), Artists::getName, pageInfo.getName())
                .eq(ObjectUtil.isNotEmpty(pageInfo.getNationality()), Artists::getNationality, pageInfo.getNationality())
                .eq(ObjectUtil.isNotEmpty(pageInfo.getSex()), Artists::getSex, pageInfo.getSex())
                .orderByDesc(Artists::getArtistId);

        Page<Artists> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page(page, queryWrapper);
        List<Artists> artistsList = page.getRecords();
        if (ObjectUtil.isEmpty(artistsList)) {
            return R.ok(artistsList);
        }
        List<Integer> artistIdList = artistsList.stream().map(Artists::getArtistId).collect(Collectors.toList());
        LambdaQueryWrapper<Albums> albumsQueryWrapper = new LambdaQueryWrapper<>();
        albumsQueryWrapper.in(Albums::getArtistId, artistIdList);
        Map<Integer, Long> albumsMap = albumsMapper.selectList(albumsQueryWrapper).stream()
                .collect(Collectors.groupingBy(Albums::getArtistId, Collectors.counting()));
        List<Integer> songIdList = artistsList.stream().map(Artists::getArtistId).collect(Collectors.toList());
        LambdaQueryWrapper<Songs> songsQueryWrapper = new LambdaQueryWrapper<>();
        songsQueryWrapper.in(Songs::getArtistId, songIdList);
        Map<Integer, Long> songMap = songsMapper.selectList(songsQueryWrapper).stream()
                .collect(Collectors.groupingBy(Songs::getArtistId, Collectors.counting()));
        List<SingerVo> singerVoList = artistsList.stream().map(artists -> {
            return new SingerVo()
                    .setArtistId(artists.getArtistId())
                    .setName(artists.getName())
                    .setBio(artists.getBio())
                    .setPicUrl(artists.getPicUrl())
                    .setNationality(artists.getNationality())
                    .setGender(CommonConstants.SEX_MAP.get(artists.getSex()))
                    .setSongs(songMap.getOrDefault(artists.getArtistId(), 0L))
                    .setAlbums(albumsMap.getOrDefault(artists.getArtistId(), 0L));
        }).collect(Collectors.toList());
        return R.ok(singerVoList, page.getTotal());
    }

    @Override
    public R getArtistById(Integer artistId) {
        if (ObjectUtil.isEmpty(artistId)) {
            return R.fail("参数错误");
        }
        Artists artists = getById(artistId);
        if (ObjectUtil.isNotEmpty(artists)) {
            SingerVo artistsVo = new SingerVo()
                    .setArtistId(artists.getArtistId())
                    .setName(artists.getName())
                    .setBio(artists.getBio())
                    .setPicUrl(artists.getPicUrl())
                    .setNationality(artists.getNationality())
                    .setGender(CommonConstants.SEX_MAP.get(artists.getSex()));
            LambdaQueryWrapper<Follow> followQueryWrapper = new LambdaQueryWrapper<>();
            followQueryWrapper.eq(Follow::getUserId, UserContext.getUser().getUserId())
                    .eq(Follow::getFollowUserId, artistId)
                    .eq(Follow::getType, 1);
            artistsVo.setIsFollow(followMapper.selectCount(followQueryWrapper) > 0);
            return R.ok(artistsVo);
        }
        return R.fail("未找到该歌手");
    }

    @Override
    public R getArtists(ArtistDto artistDto) {
        if (ObjectUtil.isEmpty(artistDto)) {
            return R.fail("参数错误");
        }
        LambdaQueryWrapper<Artists> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Artists::getSex, 0);
        if (ObjectUtil.isNotEmpty(artistDto.getInitials())) {
            queryWrapper.eq(Artists::getInitials, artistDto.getInitials());
        }
        if (ObjectUtil.isNotEmpty(artistDto.getSex())) {
            queryWrapper.eq(Artists::getSex, artistDto.getSex());
        }
        if (ObjectUtil.isNotEmpty(artistDto.getNationality())) {
            queryWrapper.eq(Artists::getNationality, artistDto.getNationality());
        }
        return R.ok(list(queryWrapper), count(queryWrapper));
    }
}




