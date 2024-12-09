package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Songs;
import com.and.music.dto.FileDto;
import com.and.music.service.SongsService;
import com.and.music.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/music")
@AllArgsConstructor
public class MusicController {

    private final SongsService songsService;
    // 音乐上传

    // 添加音乐播放量
    @PostMapping("/addPlayCount")
    public R addPlayCount(@RequestParam("songId") Integer songId) {
        return songsService.addPlayCount(songId);
    }
    @GetMapping("/hot")
    public R getHotSongs() {
        List<Songs> hotSongs = songsService.getHotSongsFromCache();
        return R.ok(hotSongs);
    }

    @GetMapping("/new")
    public R getNewSongs() {
        List<Songs> newSongs = songsService.getNewSongsFromCache();
        return R.ok(newSongs);
    }
    @PostMapping("/upload")
    public R upload(@RequestParam("title") String title,
                    @RequestParam("artistId") Integer artistId,
                    @RequestParam("albumId") Integer albumId,
                    @RequestParam("genreId") Integer genreId,
                    @RequestParam("musicPic") MultipartFile musicPic,
                    @RequestParam("musicFile") MultipartFile musicFile,
                    @RequestParam("lyricFile") MultipartFile lyricFile) {

        FileDto fileDto = new FileDto();
        fileDto.setTitle(title);
        fileDto.setArtistId(artistId);
        fileDto.setAlbumId(albumId);
        fileDto.setGenreId(genreId);
        fileDto.setMusicPic(musicPic);
        fileDto.setMusicFile(musicFile);
        fileDto.setLyricFile(lyricFile);

        return songsService.upload(fileDto);
    }

    @GetMapping("/getSongDetail")
    public R getSongDetail(@RequestParam("songId") Integer songId) {
        return songsService.getSongDetail(songId);
    }
}
