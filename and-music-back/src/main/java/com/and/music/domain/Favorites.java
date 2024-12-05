package com.and.music.domain;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
* 
* @TableName favorites 收藏
*/
@Data
@Accessors(chain = true)
public class Favorites implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer userId;

    // 1-歌曲，2-歌单
    private Integer type;

    private Integer contentId;

    private Integer createUser;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
