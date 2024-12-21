package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.CommonConstants;
import com.and.music.common.R;
import com.and.music.domain.Genres;
import com.and.music.dto.GenreDto;
import com.and.music.dto.PageInfo;
import com.and.music.mapper.GenresMapper;
import com.and.music.service.GenresService;
import com.and.music.utils.UserContext;
import com.and.music.vo.GenreVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

        if (ObjectUtil.isEmpty(genreDto)) {
            return R.fail("参数错误");
        }

        Genres genres = new Genres()
                .setType(genreDto.getType())
                .setName(genreDto.getName())
                .setDescription(genreDto.getDescription())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(genres) > 0) {
            return R.ok();
        }
        return R.fail("添加分类失败");
    }

    @Override
    public R getGenresList(Integer type) {

        if (ObjectUtil.isEmpty(type)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Genres> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Genres::getType, type);

        List<Genres> genresList = this.baseMapper.selectList(wrapper);

        return R.ok(genresList);
    }

    @Override
    public R getGenresPage(PageInfo pageInfo) {

        LambdaQueryWrapper<Genres> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(pageInfo.getName()), Genres::getName, pageInfo.getName())
                .eq(ObjectUtil.isNotEmpty(pageInfo.getType()), Genres::getType, pageInfo.getType())
                .orderByDesc(Genres::getCreateTime);

        Page<Genres> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());

        page(page, wrapper);

        List<GenreVo> genreVoList = page.getRecords().stream().map(genres -> {
            return new GenreVo()
                    .setGenreId(genres.getGenreId())
                    .setName(genres.getName())
                    .setDescription(genres.getDescription())
                    .setType(CommonConstants.GENRES_MAP.get(genres.getType()));
        }).collect(Collectors.toList());
        return R.ok(genreVoList, page.getTotal());
    }
}



