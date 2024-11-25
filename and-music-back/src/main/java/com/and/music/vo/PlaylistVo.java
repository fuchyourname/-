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
public class PlaylistVo {

    private Integer playlistId;

    private String userName;

    private String name;

    private String description;

    private String imageUrl;

    private Integer songCount;
}
