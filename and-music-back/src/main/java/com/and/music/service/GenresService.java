package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Genres;
import com.and.music.dto.GenreDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【genres】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface GenresService extends IService<Genres> {

    // 新增分类
    public R addGenres(GenreDto genreDto);
}
