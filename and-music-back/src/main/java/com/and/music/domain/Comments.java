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
 * 评论实体类
 *
 * @TableName comments 评论
 */
@Data
@Accessors(chain = true)
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer commentId;

    private Integer userId;

    private Integer otherId;

    // 1**歌曲，2**专辑，3**歌单，4**评论 5**动态
    private Integer type;

    private Integer parentId;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer createUser;
}

