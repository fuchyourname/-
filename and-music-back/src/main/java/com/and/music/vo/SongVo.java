package com.and.music.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SongVo {

    private Integer songId;

    private String title;

    private String artist;

    private String album;

    private String genre;

    private String duration;

    private String coverPath;

    private String filePath;

    private String lyricPath;

    private Boolean like;
}
