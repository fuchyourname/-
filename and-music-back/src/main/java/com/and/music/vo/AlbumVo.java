package com.and.music.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class AlbumVo {

    private Integer albumId;
    private String title;

    private String artist;

    private String coverImage;

    private String description;

    private String createUser;

    private Date createTime;

    private String type;

    private List<SongVo> songs;

}
