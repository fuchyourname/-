package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.*;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.dto.PlaylistDto;
import com.and.music.mapper.*;
import com.and.music.service.BehaviorInitializerService;
import com.and.music.service.PlaylistsService;
import com.and.music.service.SongsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.PlaylistVo;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private LikesMapper likeMapper;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private MinioUtils minioUtils;
    @Resource
    private MinioProperties minioProperties;
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
    @Resource
    private GenresMapper genresMapper;
    @Resource
    private SongsService songService;
    @Resource
    private BehaviorInitializerService behaviorInitializerService;
    @Resource
    private UserBehaviorMapper userBehaviorMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public R getRankList() {

        LambdaQueryWrapper<Playlists> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Playlists::getType, 0)
                .eq(Playlists::getUserId, 1)
                .orderByDesc(Playlists::getCreateTime);
        List<Playlists> playlistsList = this.baseMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok(new ArrayList<>());
        }
        List<PlaylistVo> playlistVoList = new ArrayList<>();
        for (Playlists playlists : playlistsList) {
            PlaylistVo playlistVo = new PlaylistVo();
            playlistVo.setPlaylistId(playlists.getPlaylistId());
            playlistVo.setName(playlists.getName())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
            if (ObjectUtil.equal(playlists.getPlaylistId(), 2)) {
                playlistVo.setSongs(songService.getHotSongsFromCache());
            } else if (ObjectUtil.equal(playlists.getPlaylistId(), 3)) {
                playlistVo.setSongs(songService.getNewSongsFromCache());
            } else {
                playlistVo.setSongs(new ArrayList<>());
            }
            playlistVoList.add(playlistVo);
        }
        return R.ok(playlistVoList);
    }

    @Override
    public R addSong(Integer songId, Integer playlistId) {

        if (ObjectUtil.isEmpty(songId) || ObjectUtil.isEmpty(playlistId)) {
            return R.fail("参数错误");
        }

        PlaylistSongs playlistSongs = new PlaylistSongs();
        // 判断是否重复添加
        LambdaQueryWrapper<PlaylistSongs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlaylistSongs::getPlaylistId, playlistId)
                .eq(PlaylistSongs::getSongId, songId);
        if (ObjectUtil.isNotEmpty(playlistSongsMapper.selectOne(queryWrapper))) {
            return R.fail("歌曲已添加");
        }
        playlistSongs.setPlaylistId(playlistId)
                .setSongId(songId)
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId());
        if (playlistSongsMapper.insert(playlistSongs) > 0) {
            return R.ok();
        }
        return R.fail("添加失败");
    }

    @Override
    public R saveOrUpdate(PlaylistDto playlistDto) {

        if (ObjectUtil.isEmpty(playlistDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(playlistDto.getPlaylistId())) {
            return addPlaylists(playlistDto);
        }
        return updatePlaylists(playlistDto);
    }

    @Override
    public R isFavorite(Integer playlistId) {

        Integer userId = UserContext.getUser().getUserId();
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getUserId, userId)
                .eq(Favorites::getContentId, playlistId)
                .eq(Favorites::getType, 2);
        if (ObjectUtil.isNotEmpty(favoritesMapper.selectOne(queryWrapper))) {
            return R.ok(true);
        }
        return R.ok(false);
    }

    @Override
    public R getFavoriteSongs(Integer playlistId) {

        LambdaQueryWrapper<PlaylistSongs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlaylistSongs::getPlaylistId, playlistId);
        List<PlaylistSongs> playlistSongsList = playlistSongsMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(playlistSongsList)) {
            return R.ok(new ArrayList<>());
        }
        List<Integer> songIds = playlistSongsList.stream().map(PlaylistSongs::getSongId).collect(Collectors.toList());
        LambdaQueryWrapper<Likes> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Likes::getUserId, UserContext.getUser().getUserId())
                .in(Likes::getContentId, songIds)
                .eq(Likes::getType, 1);
        List<Likes> likesList = likeMapper.selectList(queryWrapper1);
        if (ObjectUtil.isEmpty(likesList)) {
            return R.ok(new ArrayList<>());
        }
        List<Integer> likeIds = likesList.stream().map(Likes::getContentId).collect(Collectors.toList());
        return R.ok(likeIds);
    }

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
        if (ObjectUtil.isNotEmpty(playlistDto.getPlaylistId())) {
            return updatePlaylists(playlistDto);
        }
        List<Playlists> playlistsList =
                list(new QueryWrapper<Playlists>().eq("name", name));
        if (ObjectUtil.isNotEmpty(playlistsList)) {
            return R.fail("歌单名称重复");
        }
        Playlists playlists = new Playlists();
        playlists.setName(name);
        playlists.setDescription(playlistDto.getDescription());
        playlists.setUserId(UserContext.getUser().getUserId());
        playlists.setType(1);
        playlists.setCreateUser(UserContext.getUser().getUserId());
        playlists.setUpdateUser(UserContext.getUser().getUserId());
        playlists.setSongCount(0);
        playlists.setPlayCount(0L);
        if (ObjectUtil.isEmpty(playlistDto.getType())) {
            playlists.setStatus(1);
        }
        this.baseMapper.insert(playlists);

        try {
            MultipartFile pl = playlistDto.getImage();
            if (pl != null) {
                // 上传歌曲图片
                FileDto fileDto = new FileDto();
                fileDto.setMusicPic(pl);
                fileDto.setGenreId(3);
                String objectPath = PathUtils.getCoverPath(fileDto, playlistDto.getPlaylistId());

                String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                        objectPath, fileDto.getMusicPic());
                playlists.setImageUrl(coverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
        this.baseMapper.updateById(playlists);
        return R.ok();
    }

    @Override
    public R updatePlaylists(PlaylistDto playlistDto) {
        if (ObjectUtil.isEmpty(playlistDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(playlistDto.getPlaylistId())) {
            return R.fail("参数错误");
        }
        Playlists playlists = this.baseMapper.selectById(playlistDto.getPlaylistId());
        playlists.setName(playlistDto.getName());
        playlists.setDescription(playlistDto.getDescription());
        playlists.setUpdateUser(UserContext.getUser().getUserId());

        if (ObjectUtil.isNotEmpty(playlistDto.getImage())) {
            try {
                // http://192.168.154.1:9000/music/cover/1/27_1732354594609.jpg
                String objectName = playlists.getImageUrl().split("/")[3];
                minioUtils.removeObject(minioProperties.getBucket(), objectName);
                MultipartFile pl = playlistDto.getImage();
                if (pl != null) {
                    // 上传歌曲图片
                    FileDto fileDto = new FileDto();
                    fileDto.setMusicPic(pl);
                    fileDto.setGenreId(3);
                    String objectPath = PathUtils.getCoverPath(fileDto, playlistDto.getPlaylistId());

                    String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                            objectPath, fileDto.getMusicPic());
                    playlists.setImageUrl(coverUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("上传失败");
            }
        }
        this.baseMapper.updateById(playlists);
        return R.ok(playlists);
    }

    @Override
    public R getPlaylistPage(PageInfo pageInfo) {

        LambdaQueryWrapper<Playlists> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(pageInfo.getName()), Playlists::getName, pageInfo.getName())
                .eq(Playlists::getStatus, 1)
//                .ne(Playlists::getType, 0)
                .orderByDesc(Playlists::getPlayCount);
        Page<Playlists> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page(page, queryWrapper);
        List<Playlists> playlistsList = page.getRecords();
        List<Integer> createUserList = playlistsList.stream().map(Playlists::getUserId).collect(Collectors.toList());
        List<Integer> genresIds = playlistsList.stream().map(Playlists::getType).collect(Collectors.toList());
        List<Users> usersList = usersMapper.selectBatchIds(createUserList);
        List<Genres> genresList = genresMapper.selectBatchIds(genresIds);
        Map<Integer, Genres> genresMap = genresList.stream().collect(Collectors.toMap(Genres::getGenreId, genres -> genres));
        Map<Integer, Users> usersMap = usersList.stream().collect(Collectors.toMap(Users::getUserId, users -> users));
        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setUserName(usersMap.get(playlists.getUserId()).getUserName())
                    .setName(playlists.getName())
                    .setPlayCount(playlists.getPlayCount())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl())
                    .setType(ObjectUtil.isNotEmpty(genresMap.get(playlists.getType())) ?
                            genresMap.get(playlists.getType()).getName() : "")
                    .setSongCount(playlists.getSongCount())
                    ;
        }).collect(Collectors.toList());
        return R.ok(playlistVoList, page.getTotal());
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
        behaviorInitializerService.
                recordUserBehavior(UserContext.getUser().getUserId(), playlistId, "BROWSE");
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
                    .setDuration(songs.getDuration())
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

        List<Playlists> playlistsList = this.list(new QueryWrapper<Playlists>()
                .eq("status", 1)
                .ne("type", 0)
                .eq("type", type));

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok("暂无该类型的歌单");
        }

        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
            return new PlaylistVo()
                    .setPlaylistId(playlists.getPlaylistId())
                    .setName(playlists.getName())
                    .setUserId(playlists.getUserId())
                    .setDescription(playlists.getDescription())
                    .setImageUrl(playlists.getImageUrl());
        }).collect(Collectors.toList());

        List<Integer> userIds = playlistsList.stream().
                map(Playlists::getUserId).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(userIds)) {
            return R.ok(playlistVoList);
        }

        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        Map<Integer, Users> usersMap = usersList.stream().collect(Collectors.toMap(Users::getUserId, users -> users));

        playlistVoList.forEach(playlistVo -> {
            playlistVo.setUserName(usersMap.get(playlistVo.getUserId()).getUserName());
            playlistVo.setUserAvatar(usersMap.get(playlistVo.getUserId()).getPicUrl());
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


    @Override
    public R addPlayCount(Integer playlistId, Integer songId) {

        if (ObjectUtil.isEmpty(songId)) {
            return R.fail("参数错误");
        }
        behaviorInitializerService.
                recordUserBehavior(UserContext.getUser().getUserId(), playlistId, "PLAY");
        redisTemplate.opsForHash().increment("PlayListPlayCount", playlistId.toString(), 1);

        return R.ok();
    }

    @Override
    public void savePlayCount(Integer playlistId) {

        if (ObjectUtil.isEmpty(playlistId)) {
            return;
        }
        Playlists playlists = this.getById(playlistId);
        if (ObjectUtil.isNotEmpty(playlists)) {
            // 在原有的基础上新增redis中的值
            playlists.setPlayCount(playlists.getPlayCount() +
                    Long.parseLong(redisTemplate.opsForHash().get("PlayListPlayCount", playlistId.toString()).toString()));
            this.baseMapper.updateById(playlists);
            redisTemplate.opsForHash().delete("PlayListPlayCount", playlistId.toString());
        }
    }
}




