package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.*;
import com.and.music.dto.PlaylistDto;
import com.and.music.mapper.*;
import com.and.music.service.PlaylistsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.PlaylistVo;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author and
 * @description 针对表【playlists】的数据库操作Service实现
 * @createDate 2024-10-14 15:31:30
 */
@Service
public class PlaylistsServiceImpl extends ServiceImpl<PlaylistsMapper, Playlists>
        implements PlaylistsService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private MinioUtils minioUtils;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private ArtistsMapper artistsMapper;
    @Resource
    private SongsMapper songsMapper;
    @Resource
    private PlaylistSongsMapper playlistSongsMapper;
    @Resource
    private FavoritesMapper favoritesMapper;

    @Override
    @Transactional
    public R addPlaylists(PlaylistDto playlistDto) {
        /*
         * 1、判断歌单名称是否重复
         * 2、判断歌单封面是否上传成功
         * 3、保存歌单信息
         * 4、返回结果
         */
        if (ObjectUtil.isEmpty(playlistDto)) {
            return R.fail("参数错误");
        }
        String name = playlistDto.getName();
        if (ObjectUtil.isEmpty(name)) {
            return R.fail("歌单名称不能为空");
        }
        List<Playlists> playlistsList =
                list(new QueryWrapper<Playlists>().eq("name", name));
        if (ObjectUtil.isNotEmpty(playlistsList)) {
            return R.fail("歌单名称重复");
        }
        Playlists playlists = new Playlists();
        playlists.setName(name);
        playlists.setDescription(playlistDto.getDescription());
//        playlists.setUserId(UserContext.getUser().getUserId());
        playlists.setUserId(2);
        this.baseMapper.insert(playlists);

        try {
            MultipartFile pl = playlistDto.getImage();
            if (pl != null) {
                // 上传歌曲图片
                String bucketName = "music";
                String objectPath = "cover/" + playlistDto.getType() + "/" + playlists.getPlaylistId() + ".jpg";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String coverUrl = minioUtils.putObject(bucketName, objectPath, pl);

                playlists.setImageUrl(coverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
        this.baseMapper.updateById(playlists);

        playlistSongsMapper.insertBatch(playlists.getPlaylistId(), playlistDto.getSongIds());

        return R.ok();
    }
    @Override
    public R getRecommendPlaylists() {
        /**
         * 1、获取歌单表中前十条记录
         * 2. 根据用户id获取用户信息
         * 3. 构造返回结果
         */
        List<Playlists> playlistsList =
                this.list(new QueryWrapper<Playlists>().last("limit 10"));

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok("暂无推荐歌单");
        }
        List<Integer> userIds = playlistsList.stream().
                map(Playlists::getUserId).collect(Collectors.toList());
        if (ObjectUtil.isEmpty(userIds)) {
            return R.fail("获取推荐歌单失败");
        }
        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setUserName(usersList.stream().filter(users -> users.getUserId().equals(playlists.getUserId())).findFirst().get().getUserName())
                    .setName(playlists.getName())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
        }).collect(Collectors.toList());

        return R.ok(playlistVoList);
    }

    @Override
    public R getPlaylistDetail(Integer playlistId) {

        if (ObjectUtil.isEmpty(playlistId)) {
            return R.fail("参数错误");
        }
        PlaylistVo playlistVo = new PlaylistVo();

        Playlists playlists = getById(playlistId);
        if (ObjectUtil.isEmpty(playlists)) {
            return R.fail("未找到该歌单");
        }
        playlistVo.setPlaylistId(playlists.getPlaylistId())
                .setName(playlists.getName())
                .setDescription(playlists.getDescription())
                .setImageUrl(playlists.getImageUrl());
        Users users = usersMapper.selectById(playlists.getUserId());
        if (ObjectUtil.isNotEmpty(users)) {
            playlistVo.setUserName(users.getUserName());
            playlistVo.setUserAvatar(users.getPicUrl());
        }
        List<PlaylistSongs> playlistSongsList =
                playlistSongsMapper.selectList(
                        new QueryWrapper<PlaylistSongs>().eq("playlist_id", playlistId));
        if (ObjectUtil.isEmpty(playlistSongsList)) {
            return R.ok(playlistVo);
        }
        List<Integer> songIds = playlistSongsList.
                stream().map(PlaylistSongs::getSongId).collect(Collectors.toList());
        List<Songs> songsList = songsMapper.selectBatchIds(songIds);
        if (ObjectUtil.isEmpty(songsList)) {
            return R.ok(playlistVo);
        }
        List<Integer> albumIds = songsList.stream().map(Songs::getAlbumId).collect(Collectors.toList());
        List<Integer> artistIds = songsList.stream().map(Songs::getArtistId).collect(Collectors.toList());
        List<Albums> albumsList = albumsMapper.selectBatchIds(albumIds);
        List<Artists> artistsList = artistsMapper.selectBatchIds(artistIds);
        Map<Integer, Albums> albumsMap = albumsList.stream().collect(Collectors.toMap(Albums::getAlbumId, albums -> albums));
        Map<Integer, Artists> artistsMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, artists -> artists));
        playlistVo.setSongs(songsList.stream().map(songs -> {
            return new SongVo()
                    .setSongId(songs.getSongId())
                    .setTitle(songs.getTitle())
                    .setAlbum(albumsMap.get(songs.getAlbumId()).getTitle())
                    .setArtist(artistsMap.get(songs.getArtistId()).getName())
                    .setCoverPath(songs.getCoverPath())
                    .setFilePath(songs.getFilePath())
                    .setLyricPath(songs.getLyricPath());
        }).collect(Collectors.toList()));
        return R.ok(playlistVo);
    }

    @Override
    public R getPlaylistsByType(Integer type) {

        if (ObjectUtil.isEmpty(type)) {
            return R.fail("参数错误");
        }

        List<Playlists> playlistsList = this.list(new QueryWrapper<Playlists>().eq("type", type));

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok("暂无该类型的歌单");
        }

        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setName(playlists.getName())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
        }).collect(Collectors.toList());

        List<Integer> userIds = playlistsList.stream().
                map(Playlists::getUserId).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(userIds)) {
            return R.ok(playlistVoList);
        }

        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        playlistVoList.forEach(playlistVo -> {
            usersList.stream().filter(users -> users.getUserId().equals(playlistVo.getPlaylistId())).findFirst().ifPresent(users -> {
                playlistVo.setUserName(users.getUserName());
                playlistVo.setUserAvatar(users.getPicUrl());
            });
        });

        return R.ok(playlistVoList);
    }

    @Override
    public R getUserPlaylists() {

        LambdaQueryWrapper<Playlists> queryWrapper = new LambdaQueryWrapper<Playlists>()
                .eq(Playlists::getUserId, UserContext.getUser().getUserId());
        List<Playlists> playlistsList = this.list(queryWrapper);

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok();
        }
        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setName(playlists.getName())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
        }).collect(Collectors.toList());

        return R.ok(playlistVoList);
    }

    @Override
    public R getUserFavorites() {

        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<Favorites>()
                .eq(Favorites::getUserId, UserContext.getUser().getUserId())
                .eq(Favorites::getType, 2);

        List<Favorites> favoritesList = favoritesMapper.selectList(queryWrapper);

        if (ObjectUtil.isEmpty(favoritesList)) {
            return R.ok();
        }

        List<Integer> playlistIds = favoritesList.stream().
                map(Favorites::getContentId).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(playlistIds)) {
            return R.ok();
        }

        List<Playlists> playlistsList = this.list(
                new QueryWrapper<Playlists>().in("playlist_id", playlistIds));

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok();
        }

        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setName(playlists.getName())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
        }).collect(Collectors.toList());

        return R.ok(playlistVoList);
    }
}




