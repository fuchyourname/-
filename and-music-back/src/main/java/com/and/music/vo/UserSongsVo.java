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
public class UserSongsVo {

    private Integer userSongsId;
    private String picUrl;
    private String name;
    private String singer;
    private String songUrl;
    private String album;
    private Double size;
    private String type;
    private Date createTime;
    private Integer totalSize;
}
