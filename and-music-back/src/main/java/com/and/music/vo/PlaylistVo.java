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
public class PlaylistVo {

    private Integer playlistId;
    private Integer userId;
    private String userName;
    private String userAvatar;
    private String name;
    private String description;
    private String imageUrl;
    private String type;
    private Integer songCount;
    private Long playCount;
    private List<SongVo> songs;
}
