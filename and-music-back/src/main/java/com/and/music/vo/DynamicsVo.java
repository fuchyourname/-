package com.and.music.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class DynamicsVo {

    private Integer dynamicId;
    private String userName;
    private String userAvatar;
    private String content;
    private SongVo song;
    private Date createTime;

}
