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
* @TableName playlists 歌单
*/
@Data
@Accessors(chain = true)
public class Playlists implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer playlistId;

    private Integer userId;

    private String name;

    private String description;

    private Integer type;

    private String imageUrl;

    private Integer songCount;

    private Long playCount;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
