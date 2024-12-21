package com.and.music.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CommentDto {

    private Integer commentId;

    private Integer otherId;

    // 1**歌曲，2**专辑，3**歌单，4**评论
    private Integer type;

    private Integer parentId;

    private String content;
}
