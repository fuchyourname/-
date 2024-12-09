package com.and.music.domain;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import java.util.Date;


/**
*
* @TableName songs 歌曲
*/
@Data
@Accessors(chain = true)
public class Songs implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer songId;

    private String title;

    private Integer artistId;

    private Integer albumId;

    private Integer genreId;

    private String duration;

    private Long playCount;

    private String coverPath;

    private String filePath;

    private String lyricPath;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
