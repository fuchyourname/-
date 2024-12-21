package com.and.music.controller;


import com.and.music.common.R;
import com.and.music.domain.Albums;
import com.and.music.dto.AlbumDto;
import com.and.music.dto.PageInfo;
import com.and.music.service.AlbumsService;
import com.and.music.service.GenresService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/album")
@AllArgsConstructor
public class AlbumsController {

    private final AlbumsService albumsService;
    private final GenresService genresService;

    // 根据名称搜索专辑
    @GetMapping("/getAlbumsByName/{name}")
    public R getAlbumsByName(@PathVariable("name") String name) {
        return albumsService.getAlbumsByName(name);
    }
    // 获取专辑所有的分类
    @GetMapping("/getGenresList")
    public R getGenresList() {
        return genresService.getGenresList(4);
    }
    // 添加专辑
    @PostMapping("/addAlbum")
    public R addAlbum(@RequestBody AlbumDto albumDto) {
        return albumsService.addAlbum(albumDto);
    }

    // 更新专辑
 @PostMapping("/updateAlbum")
public R updateAlbum(
         @RequestParam("albumId") Integer albumId,
         @RequestParam("title") String title,
         @RequestParam(name = "artistId", required = false) Integer artistId,
         @RequestParam("type") Integer type,
         @RequestParam("description") String description,
         @RequestParam("releaseDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate, // 指定日期格式
         @RequestParam(name = "coverImage", required = false) MultipartFile coverImage
) {
    AlbumDto albumDto = new AlbumDto()
            .setAlbumId(albumId)
            .setTitle(title)
            .setArtistId(artistId)
            .setType(type)
            .setCreateTime(releaseDate)
            .setDescription(description)
            .setCoverImage(coverImage);
    return albumsService.updateAlbum(albumDto);
}

    // 分页
    @PostMapping("/getAlbumPage")
    public R getAlbumPage(@RequestBody PageInfo pageInfo) {
        return albumsService.getAlbumPage(pageInfo);
    }
    // 添加专辑播放量
    @PostMapping("/addPlayCount")
    public R addPlayCount(@RequestParam("albumId") Integer albumId) {
        return albumsService.addPlayCount(albumId);
    }
}
