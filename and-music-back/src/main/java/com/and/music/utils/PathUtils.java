package com.and.music.utils;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.CommonConstants;
import com.and.music.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

public class PathUtils {

    public static String getCoverPath(Integer id, FileDto fileDto) {

        MultipartFile musicPic = fileDto.getMusicPic();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s/%s.%s",
                CommonConstants.IMAGE_PREFIX, fileDto.getGenreId(), id, fileSuffix);
    }

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

        MultipartFile musicPic = fileDto.getMusicFile();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s.%s",
                CommonConstants.MUSIC_PREFIX, songId, fileSuffix);
    }

    public static String getLyricPath(FileDto fileDto, Integer songId) {

        MultipartFile musicPic = fileDto.getLyricFile();
        // 获取文件后缀
        String fileSuffix = musicPic.getOriginalFilename().
                substring(musicPic.getOriginalFilename().lastIndexOf(".") + 1);
        // 组成唯一文件名
        return String.format("%s/%s.%s",
                CommonConstants.LYRIC_PREFIX, songId, fileSuffix);
    }

    public static String getMusicPath(MultipartFile File) {

        // 获取文件后缀
        String fileSuffix = File.getOriginalFilename().
                substring(File.getOriginalFilename().lastIndexOf(".") + 1);
        // 获取文件前缀
        String filePrefix = File.getOriginalFilename().
                substring(0, File.getOriginalFilename().lastIndexOf("."));
        // 组成唯一文件名
        return String.format("%s/%s.%s",
                CommonConstants.MUSIC_PREFIX, filePrefix, fileSuffix);
    }
}
