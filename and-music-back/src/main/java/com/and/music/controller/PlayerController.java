package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.dto.ArtistDto;
import com.and.music.dto.PageInfo;
import com.and.music.service.ArtistsService;
import com.and.music.service.FollowService;
import com.and.music.service.GenresService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
public class PlayerController {

    private final ArtistsService artistsService;
    private final FollowService followService;
    private final GenresService genresService;

    // 根据名称查询歌手
    @GetMapping("/getPlayerByName/{name}")
    public R getPlayerByName(@PathVariable("name") String name) {
        return artistsService.getArtistsByName(name);
    }
    // 获取歌手的所有专辑
    @GetMapping("/getPlayerAlbum/{artistId}")
    public R getPlayerAlbum(@PathVariable("artistId") Integer artistId) {
        return artistsService.getAlbums(artistId);
    }
    // 获取歌手的所有歌曲
    @GetMapping("/getPlayerSong/{artistId}")
    public R getPlayerSong(@PathVariable("artistId") Integer artistId) {
        return artistsService.getSongs(artistId);
    }

    // 获取歌手的所有分类
    @GetMapping("/getPlayerType")
    public R getPlayerType() {
        return genresService.getGenresList(2);
    }

    // 修改歌手信息
    @PostMapping("/updatePlayer")
    public R saveOrUpdatePlayer(
            @RequestParam(name = "artistId", required = false) Integer artistId,
            @RequestParam("name") String name,
                          @RequestParam("bio") String bio,
                          @RequestParam(name = "pic", required = false) MultipartFile pic,
                          @RequestParam("sex") Integer sex,
                          @RequestParam("nationality") String nationality) {
        ArtistDto artistDto = new ArtistDto()
                .setArtistId(artistId)
                .setArtistId(artistId)
                .setName(name)
                .setBio(bio)
                .setPic(pic)
                .setSex(sex)
                .setNationality(nationality);
        return artistsService.updateArtist(artistDto);
    }
    // 分页歌手列表
    @PostMapping("/getPlayerPage")
    public R getPlayerPage(@RequestBody PageInfo pageInfo) {
        return artistsService.getArtistPage(pageInfo);
    }

    // 新增歌手
    @PostMapping("/addPlayer")
    public R addPlayer(@RequestParam("name") String name,
                       @RequestParam("bio") String bio,
                       @RequestParam("pic") MultipartFile pic,
                       @RequestParam("sex") Integer sex,
                       @RequestParam("nationality") String nationality) {
        ArtistDto artistDto = new ArtistDto()
                .setName(name)
                .setBio(bio)
                .setPic(pic)
                .setSex(sex)
                .setNationality(nationality);
        return artistsService.addArtist(artistDto);
    }

    // 根据ID查询歌手
    @GetMapping("/getPlayerById/{artistId}")
    public R getPlayerById(@PathVariable("artistId") Integer artistId) {
        return artistsService.getArtistById(artistId);
    }

    // 根据查询条件查询歌手
    @PostMapping("/getPlayers")
    public R getPlayers(@RequestBody ArtistDto artistDto) {
        return artistsService.getArtists(artistDto);
    }

    // 获取关注的歌手
    @GetMapping("/getFollowSinger")
    public R getFollowSinger() {
        return followService.getSingerList();
    }
}
