package com.and.music.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ArtistDto {

    private Integer artistId;

    private String name;

    private String bio;

    private MultipartFile pic;

    private Integer sex;

    private String nationality;

    private String initials;
}
