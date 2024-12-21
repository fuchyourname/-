package com.and.music.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {

    private Integer userId;

    private String username;

    private String avatar;

    private String email;

    private String description;

    private Boolean isAdmin;

    // 关注数
    private Long followCount;
    // 粉丝数
    private Long fansCount;
    // 国籍
    private String nationality;
}
