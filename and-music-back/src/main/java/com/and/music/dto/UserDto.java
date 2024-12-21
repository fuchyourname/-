package com.and.music.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class UserDto {

    private Integer userId;

    private String userName;

    private String password;

    private String description;

    private String nationality;

    private String email;

    private Integer type;

    private MultipartFile pic;
}
