package com.and.music.dto;

import lombok.Data;

@Data
public class PageInfo {

    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private String name;

    private Integer type;

    private Integer id;

    private Integer sex;

    private String nationality;

    // 起始时间
    private String startTime;

    // 终止时间
    private String endTime;
}
