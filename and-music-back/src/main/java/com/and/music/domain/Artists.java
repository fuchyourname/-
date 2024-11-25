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
* @TableName artists 艺术家
*/
@Data
@Accessors(chain = true)
public class Artists implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer artistId;

    private String name;
    /**
    *  描述
    */
    private String bio;

    private String picUrl;

    // 性别
    private Integer sex;

    // 国籍
    private String nationality;

    // 名称首字母
    private String initials;

    private Integer createUser;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
