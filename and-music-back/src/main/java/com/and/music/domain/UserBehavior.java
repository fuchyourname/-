package com.and.music.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

// 用户行为实体
@Data
@TableName("user_behavior")
@Accessors(chain = true)
public class UserBehavior {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer playlistId;
    // 行为类型，如：PLAY、COLLECT、SHARE
    private String behaviorType;

    private Double normalizedValue;

    private Integer originalValue;
}