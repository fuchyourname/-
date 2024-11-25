package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Genres;
import com.and.music.dto.GenreDto;
import com.and.music.mapper.GenresMapper;
import com.and.music.service.GenresService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【genres】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class GenresServiceImpl extends ServiceImpl<GenresMapper, Genres>
    implements GenresService {

    @Override
    public R addGenres(GenreDto genreDto) {
        return null;
    }
}




