package com.and.music.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {

    private Integer userId;

    private String username;

    private String avatar;
}
