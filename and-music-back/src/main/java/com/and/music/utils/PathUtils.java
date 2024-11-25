package com.and.music.utils;

import org.springframework.stereotype.Component;

@Component
public class PathUtils {

    // 封面图片根据类型生成路径
    public static String getCoverPath(Integer type, Integer id) {
        return "cover/" + type + "/" + id;
    }
    // 音频根据歌手id生成路径
    public static String getMusicPath(Integer id) {
        return "file/" + id;
    }
    // 歌词根据歌曲id生成路径
    public static String getLyricPath(Integer id) {
        return "lyric/" + id;
    }
}
