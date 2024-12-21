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
* @TableName genres 类型
*/
@Data
@Accessors(chain = true)
public class Genres implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer genreId;

    private String name;

    private String description;

    // 1-歌曲 2-歌手 3-歌单 4-专辑 5-用户
    private Integer type;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
