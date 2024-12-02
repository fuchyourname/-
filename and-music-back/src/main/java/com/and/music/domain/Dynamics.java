package com.and.music.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Dynamics implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer dynamicId;
    private Integer userId;
    private String content;
    private Integer musicId;
    private Integer createUser;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer updateUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
