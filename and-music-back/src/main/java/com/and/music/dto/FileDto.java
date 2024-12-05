package com.and.music.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Accessors(chain = true)
public class FileDto {

    private String title;
    private Integer artistId;
    private Integer albumId;
    private Integer genreId;
    // 音乐图片
    private MultipartFile musicPic;
    // 音频文件
    private MultipartFile musicFile;
    // 歌词文件
    private MultipartFile lyricFile;
}
