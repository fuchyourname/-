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
public class Label {
    @TableId(type = IdType.AUTO)
    private Integer labelId;
    private String name;
    private String description;
    private Integer createUser;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer updateUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
