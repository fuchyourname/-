package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.dto.ArtistDto;
import com.and.music.service.ArtistsService;
import com.and.music.service.FollowService;
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
    @PostMapping("/getPlayerById")
    public R getPlayerById(@RequestParam("artistId") Integer artistId) {
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
