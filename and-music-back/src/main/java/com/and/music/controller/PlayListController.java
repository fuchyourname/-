package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.dto.PlaylistDto;
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
            @RequestParam("image") MultipartFile image,
            @RequestParam("songIds") List<Integer> songIds) {

        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setName(name);
        playlistDto.setDescription(description);
        playlistDto.setType(type);
        playlistDto.setImage(image);
        playlistDto.setSongIds(songIds);

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
