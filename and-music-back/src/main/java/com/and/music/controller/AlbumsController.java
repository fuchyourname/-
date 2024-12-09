package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.and.music.service.AlbumsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
@AllArgsConstructor
public class AlbumsController {

    private final AlbumsService albumsService;
    // 添加专辑
    @PostMapping("/addAlbum")
    public R addAlbum(@RequestBody AlbumDto albumDto) {
        return albumsService.addAlbum(albumDto);
    }
    // 添加专辑播放量
    @PostMapping("/addPlayCount")
    public R addPlayCount(@RequestParam("albumId") Integer albumId) {
        return albumsService.addPlayCount(albumId);
    }
}
