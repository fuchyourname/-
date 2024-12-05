package com.and.music.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserSongs {
    @TableId(type = IdType.AUTO)
    private Integer userSongsId;
    private Integer userId;
    private String picUrl;
    private String name;
    private Integer singer;
    private String songUrl;
    private Integer album;
    private Integer createUser;
    private Double size;
    private Integer type;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer updateUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
