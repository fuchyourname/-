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
public class SingerVo {

    private Integer artistId;
    private String name;
    private String bio;
    private String picUrl;
    private String nationality;
    private String gender;
    private Long albums;
    private Boolean isFollow;
    private Long songs;
}
