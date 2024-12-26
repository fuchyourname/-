package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.dto.PageInfo;
import com.and.music.dto.PlaylistDto;
import com.and.music.service.FavoritesService;
import com.and.music.service.GenresService;
import com.and.music.service.PlaylistsService;
import com.and.music.service.SongsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/playList")
@AllArgsConstructor
@Slf4j
public class PlayListController {

    private final PlaylistsService playlistsService;
    private final GenresService genresService;
    private final FavoritesService favoritesService;

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
    @PostMapping("/addPlayCount")
    public R addPlayCount(@RequestParam("playlistId") Integer playlistId) {
        return playlistsService.addPlayCount(playlistId);
    }
    // 根据类型获取歌单
    @GetMapping("/getPlayListByType")
    public R getPlayListByType(@RequestParam("type") Integer type) {
        return playlistsService.getPlaylistsByType(type);
    }
    // 获取推荐歌单
    @GetMapping("/getRecommendPlayList")
    public R getRecommendPlayList() {
        return playlistsService.getRecommendPlaylists();
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
