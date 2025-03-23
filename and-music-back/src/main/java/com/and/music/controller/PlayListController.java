package com.and.music.controller;


import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Label;
import com.and.music.domain.Playlists;
import com.and.music.dto.PageInfo;
import com.and.music.dto.PlaylistDto;
import com.and.music.mapper.LabelMapper;
import com.and.music.service.*;
import com.and.music.utils.UserContext;
import com.and.music.vo.PlaylistVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playList")
@AllArgsConstructor
@Slf4j
public class PlayListController {

    private final PlaylistsService playlistsService;
    private final GenresService genresService;
    private final FavoritesService favoritesService;
    private final CFRecommenderService cfRecommenderService;
    private final LabelMapper labelMapper;

    // 新增
    @PostMapping("/add")
    public R addOrUpdate(
            @RequestParam(name = "playlistId", required = false) Integer playlistId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(name = "image", required = false) MultipartFile image) {

        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setPlaylistId(playlistId);
        playlistDto.setName(name);
        playlistDto.setDescription(description);
        playlistDto.setImage(image);

        return playlistsService.addPlaylists(playlistDto);
            }

    // 修改歌单
    @PostMapping("/update")
    public R update(
            @RequestParam("playlistId") Integer playlistId,
            @RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam(name = "image", required = false) MultipartFile image) {
        PlaylistDto playlistDto = new PlaylistDto()
                .setPlaylistId(playlistId)
                .setName(name)
                .setDescription(description)
                .setImage(image);
        return playlistsService.updatePlaylists(playlistDto);
    }

    // 歌单添加歌曲
    @PostMapping("/addSong")
    public R addSong(@RequestParam("songId") Integer songId,
                     @RequestParam("playlistId") Integer playlistId) {
        return playlistsService.addSong(songId, playlistId);
    }

    // 新增或更新歌单
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody PlaylistDto playlistDto) {
        return playlistsService.saveOrUpdate(playlistDto);
    }

    // 收藏歌单
    @GetMapping("/favorite")
    public R favorite(@RequestParam("playlistId") Integer playlistId) {
        return favoritesService.addFavorites(playlistId);
    }
    // 取消收藏歌单
    @GetMapping("/cancelFavorite")
    public R cancelFavorite(@RequestParam("playlistId") Integer playlistId) {
        return favoritesService.removeFavorites(playlistId);
    }

    // 歌单是否收藏
    @GetMapping("/isFavorite")
    public R isFavorite(@RequestParam("playlistId") Integer playlistId) {
        return playlistsService.isFavorite(playlistId);
    }
    // 歌单中点赞的歌曲
    @GetMapping("/getFavoriteSongs")
    public R getFavoriteSongs(@RequestParam("playlistId") Integer playlistId) {
        return playlistsService.getFavoriteSongs(playlistId);
    }

    // 获取歌单的所有分类
    @GetMapping("/getGenresList")
    public R getGenresList() {
        return genresService.getGenresList(3);
    }

    // 修改歌单
    @PostMapping("/updatePlayList")
    public R updatePlayList(@RequestBody PlaylistDto playlistDto) {
        return playlistsService.updatePlaylists(playlistDto);
    }
    // 分页歌单
    @PostMapping("/getPlayListPage")
    public R getPlayListPage(@RequestBody PageInfo pageInfo) {
        return playlistsService.getPlaylistPage(pageInfo);
    }

    // 添加歌单播放量
    @GetMapping("/addPlayCount")
    public R addPlayCount(@RequestParam("playlistId") Integer playlistId,
                          @RequestParam("songId") Integer songId
    ) {
        return playlistsService.addPlayCount(playlistId, songId);
    }
    // 根据类型获取歌单
    @GetMapping("/getPlayListByType")
    public R getPlayListByType(@RequestParam("type") Integer type) {
        return playlistsService.getPlaylistsByType(type);
    }
    // 获取推荐歌单
    @GetMapping("/getRecommendPlayList")
    public R getRecommendPlayList() {
        List<Playlists> playlists = cfRecommenderService.recommend(UserContext.getUser().getUserId(), 10);
        List<Integer> playlistIds = playlists.stream().map(Playlists::getPlaylistId).collect(Collectors.toList());
        LambdaQueryWrapper<Label> labelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isEmpty(playlistIds)) {
            return R.ok();
        }else {
            if (playlistIds.size() == 1) {
                labelLambdaQueryWrapper.eq(Label::getContentId, playlistIds.get(0))
                        .eq(Label::getType, 0);
            } else {
                labelLambdaQueryWrapper.in(Label::getContentId, playlistIds)
                        .eq(Label::getType, 0);
            }
        }
        List<Label> labels = labelMapper.selectList(labelLambdaQueryWrapper);
        List<PlaylistVo> playlistVos = playlists.stream().map(playlist -> {
            return new PlaylistVo()
                    .setPlaylistId(playlist.getPlaylistId())
                    .setName(playlist.getName())
                    .setDescription(playlist.getDescription())
                    .setImageUrl(playlist.getImageUrl())
                    .setPlayCount(playlist.getPlayCount())
                    .setLabels(labels.stream().filter(label -> label.getContentId().equals(playlist.getPlaylistId()))
                            .map(Label::getName).collect(Collectors.toList()));
        }).collect(Collectors.toList());
        return R.ok(playlistVos);
    }
    // 新增歌单
    @PostMapping("/addPlayList")
    public R addPlayList(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") Integer type,
            @RequestParam("image") MultipartFile image) {

        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setName(name);
        playlistDto.setDescription(description);
        playlistDto.setType(type);
        playlistDto.setImage(image);

        return playlistsService.addPlaylists(playlistDto);
    }
    // 获取歌单详情
    @GetMapping("/getPlaylistDetail")
    public R getPlaylistDetail(@RequestParam("playlistId") Integer playlistId) {
        return playlistsService.getPlaylistDetail(playlistId);
    }
    // 获取用户创建的歌单
    @GetMapping("/getUserPlaylists")
    public R getUserPlaylists() {
        return playlistsService.getUserPlaylists();
    }
    // 获取用户收藏的歌单
    @GetMapping("/getUserFavorites")
    public R getUserFavorites() {
        return playlistsService.getUserFavorites();
    }
}
