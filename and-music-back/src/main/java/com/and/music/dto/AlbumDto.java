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
public class AlbumDto {

    private String title;

    private Integer artistId;

    private MultipartFile coverImage;

    private String description;

}
