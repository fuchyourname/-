package com.and.music.utils;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.CommonConstants;
import com.and.music.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

public class PathUtils {

    public static String getCoverPath(FileDto fileDto, Integer songId) {

        MultipartFile musicPic = fileDto.getMusicPic();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s/%s.%s",
                CommonConstants.IMAGE_PREFIX, fileDto.getGenreId(), songId, fileSuffix);
    }

    public static String getMusicPath(FileDto fileDto, Integer songId) {

        MultipartFile musicPic = fileDto.getMusicPic();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s.%s",
                CommonConstants.MUSIC_PREFIX, songId, fileSuffix);
    }

    public static String getLyricPath(FileDto fileDto, Integer songId) {

        MultipartFile musicPic = fileDto.getMusicPic();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s.%s",
                CommonConstants.LYRIC_PREFIX, songId, fileSuffix);
    }
}
