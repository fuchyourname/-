package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.dto.ArtistDto;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.service.ArtistsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
* @author and
* @description 针对表【artists】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class ArtistsServiceImpl extends ServiceImpl<ArtistsMapper, Artists>
    implements ArtistsService {

    @Resource
    private MinioUtils minioUtils;
    @Override
    public R addArtist(ArtistDto artistDto) {
        /*
        * 1、判断歌手是否已经存在
        * 2、判断歌手头像是否上传成功
        * 3、保存歌手信息
        * 4、返回结果
         */
        if (ObjectUtil.isEmpty(artistDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(artistDto.getName())) {
            return R.fail("歌手名称不能为空");
        }
        if (ObjectUtil.isEmpty(artistDto.getPic())) {
            return R.fail("歌手头像不能为空");
        }
        List<Artists> artistsList =
                list(new QueryWrapper<Artists>().eq("name", artistDto.getName()));
        if (ObjectUtil.isNotEmpty(artistsList)) {
            return R.fail("歌手名称重复");
        }
        Artists artists = new Artists();
        artists.setName(artistDto.getName());
        artists.setBio(artistDto.getBio());
        artists.setSex(artistDto.getSex());
        artists.setNationality(artistDto.getNationality());
        artists.setCreateUser(1);
        artists.setUpdateUser(1);
        artists.setInitials(PinyinUtil.getFirstLetters(artistDto.getName()));

        if (!save(artists)) {
            return R.fail("添加失败");
        }

        try {
            MultipartFile pl = artistDto.getPic();
            if (pl != null) {
                // 上传歌曲图片
                String bucketName = "music";
                String objectPath = "cover/" + 1 + "/" + artists.getArtistId() + ".jpg";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String coverUrl = minioUtils.putObject(bucketName, objectPath, pl);

                artists.setPicUrl(coverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }

        if (updateById(artists)) {
            return R.ok();
        }
        return R.fail("添加失败");
    }

    @Override
    public R getArtistById(Integer artistId) {
        if (ObjectUtil.isEmpty(artistId)) {
            return R.fail("参数错误");
        }
        Artists artists = getById(artistId);
        if (ObjectUtil.isNotEmpty(artists)) {
            return R.ok(artists);
        }
        return R.fail("未找到该歌手");
    }

    @Override
    public R getArtists(ArtistDto artistDto) {
        if (ObjectUtil.isEmpty(artistDto)) {
            return R.fail("参数错误");
        }
        LambdaQueryWrapper<Artists> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(artistDto.getInitials())) {
            queryWrapper.eq(Artists::getInitials, artistDto.getInitials());
        }
        if (ObjectUtil.isNotEmpty(artistDto.getSex())) {
            queryWrapper.eq(Artists::getSex, artistDto.getSex());
        }
        if (ObjectUtil.isNotEmpty(artistDto.getNationality())) {
            queryWrapper.eq(Artists::getNationality, artistDto.getNationality());
        }
        return R.ok(list(queryWrapper), count(queryWrapper));
    }

    @Override
    public R getArtistByName(String artistName) {
        return null;
    }
}




