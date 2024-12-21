package com.and.music.common;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

    // 图片文件存储前缀
    public static final String IMAGE_PREFIX = "cover";
    // 音乐文件存储前缀
    public static final String MUSIC_PREFIX = "file";
    // 歌词文件存储前缀
    public static final String LYRIC_PREFIX = "lyric";
    // 管理员账号
    public static final String ADMIN_USERNAME = "admin";

    public static final Map<Integer, String> SEX_MAP = new HashMap<>();
    static {
        SEX_MAP.put(1, "男");
        SEX_MAP.put(2, "女");
        SEX_MAP.put(3, "组合");
    }

    public static final Map<Integer, String> GENRES_MAP = new HashMap<>();
    static {
        GENRES_MAP.put(1, "歌曲");
        GENRES_MAP.put(2, "歌手");
        GENRES_MAP.put(3, "歌单");
        GENRES_MAP.put(4, "专辑");
    }
}
