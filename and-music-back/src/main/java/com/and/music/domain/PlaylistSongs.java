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
* @TableName playlist_songs 歌单内歌曲表
*/
@Data
@Accessors(chain = true)
public class PlaylistSongs implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer playlistId;

    private Integer songId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
