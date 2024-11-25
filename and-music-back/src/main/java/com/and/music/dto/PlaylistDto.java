package com.and.music.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class PlaylistDto {

    private String name;

    private String description;

    private Integer type;

    private MultipartFile image;

    private List<Integer> songIds;
}
