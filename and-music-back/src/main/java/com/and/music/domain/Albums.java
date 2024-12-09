package com.and.music.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import java.util.Date;

/**
* 
* @TableName albums 歌曲专辑
*/
@Data
@Accessors(chain = true)
public class Albums implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer albumId;

    private String title;

    private Integer artistId;

    private String coverImage;

    private String description;

    private Integer status;

    private Integer createUser;

    private Long playCount;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
