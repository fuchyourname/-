package com.and.music.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserSongs {

    private Integer userSongsId;
    private Integer userId;
    private String picUrl;
    private String name;
    private String singer;
    private String songUrl;
    private String album;
    private Integer createUser;
    private Double size;
    private String type;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer updateUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
