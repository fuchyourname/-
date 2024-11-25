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
* @TableName users 用户
*/
@Data
@Accessors(chain = true)
public class Users implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String password;

    private String email;

    private String picUrl;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
