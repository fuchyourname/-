package com.and.music.vo;


import com.and.music.domain.Comments;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class CommentVo {

    private Integer commentId;

    private Integer userId;

    private String userName;

    private String userPic;

    private Integer otherId;

    private Integer parentId;

    private String content;

    private Date createTime;

    private Integer likeCount; // 评论点赞数

    // 回复数
    private Integer replyCount;

    private List<CommentVo> comments;
}
