package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.Albums;
import com.and.music.domain.Artists;
import com.and.music.domain.UserSongs;
import com.and.music.domain.Users;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.mapper.AlbumsMapper;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.mapper.UserSongsMapper;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.UserSongsService;
import com.and.music.service.UsersService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.FileVo;
import com.and.music.vo.UserSongsVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserSongsServiceImpl extends ServiceImpl<UserSongsMapper, UserSongs>
        implements UserSongsService {
    @Resource
    private MinioUtils minioUtils;
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private ArtistsMapper artistsMapper;

    @Override
   public R download(Integer songId) {
       LambdaQueryWrapper<UserSongs> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(UserSongs::getUserSongsId, songId);
       UserSongs userSongs = this.baseMapper.selectOne(queryWrapper);
       if (ObjectUtil.isEmpty(userSongs)) {
           return R.fail("歌曲不存在");
       }
       String url = userSongs.getSongUrl();
       int startIndex = url.indexOf("/music/") + "/music/".length();
       String objectName = url.substring(startIndex);

       try (InputStream inputStream = minioUtils.getObject(minioProperties.getBucket(), objectName)) {
           // 你可以在这里处理 inputStream，例如将其转换为字节数组或其他格式
           byte[] content = inputStream.readAllBytes();
           String base64Content = Base64.getEncoder().encodeToString(content);
           FileVo fileVo = new FileVo();
           fileVo.setFile(base64Content);
           fileVo.setFileName(userSongs.getName());
           fileVo.setFileType(userSongs.getType());
           return R.ok(fileVo);
       } catch (Exception e) {
           log.error("下载歌曲失败", e);
           return R.fail("下载失败");
       }
   }


    @Override
    @Transactional
    public R addUserSongs(MultipartFile file) {

        // 上传歌曲文件
        String musicUrl = "";
        try {
            String objectPath = PathUtils.getMusicPath(file);

            musicUrl = minioUtils.putObject(minioProperties.getBucket(),
                    objectPath, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UserSongs userSongs = new UserSongs();
        userSongs.setUserId(UserContext.getUser().getUserId());
        userSongs.setPicUrl("");
        userSongs.setSongUrl(musicUrl);
        // 获取文件前缀
        String filePrefix = file.getOriginalFilename().
                substring(0, file.getOriginalFilename().lastIndexOf("."));
        userSongs.setName(filePrefix);
        userSongs.setAlbum(8);
        userSongs.setSinger(2);
        userSongs.setSize(Double.valueOf(file.getSize() / 1024 / 1024));
        // 获取文件后缀
        String fileSuffix = file.getOriginalFilename().
                substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        userSongs.setType(fileSuffix);
        userSongs.setCreateUser(UserContext.getUser().getUserId());
        userSongs.setUpdateUser(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(userSongs) > 0) {
            return R.ok();
        }
        return R.fail("上传失败");
    }

    @Override
    public R getUserSongsPage(PageInfo pageInfo) {

        LambdaQueryWrapper<UserSongs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSongs::getUserId, UserContext.getUser().getUserId())
                .orderByDesc(UserSongs::getCreateTime);
        Page<UserSongs> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page(page, queryWrapper);
        List<UserSongs> userSongsList = page.getRecords();
        if (ObjectUtil.isEmpty(userSongsList)) {
            return R.ok(userSongsList);
        }
        List<Integer> albumsIds = userSongsList.stream().map(UserSongs::getAlbum).collect(Collectors.toList());
        List<Integer> singersIds = userSongsList.stream().map(UserSongs::getSinger).collect(Collectors.toList());
        List<Albums> albumsList = albumsMapper.selectBatchIds(albumsIds);
        List<Artists> artistsList = artistsMapper.selectBatchIds(singersIds);
        Map<Integer, Albums> albumsMap = albumsList.stream().collect(Collectors.toMap(Albums::getAlbumId, albums -> albums));
        Map<Integer, Artists> artistsMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, artists -> artists));
        List<UserSongsVo> userSongsVoList = userSongsList.stream().map(userSongs -> {
            return new UserSongsVo()
                    .setUserSongsId(userSongs.getUserSongsId())
                    .setPicUrl(userSongs.getPicUrl())
                    .setName(userSongs.getName())
                    .setSinger(artistsMap.get(userSongs.getSinger()).getName())
                    .setSongUrl(userSongs.getSongUrl())
                    .setAlbum(albumsMap.get(userSongs.getAlbum()).getTitle())
                    .setSize(userSongs.getSize())
                    .setType(userSongs.getType())
                    .setCreateTime(userSongs.getCreateTime());
        }).collect(Collectors.toList());

        Double totalSize = userSongsVoList.stream().mapToDouble(UserSongsVo::getSize).sum() / 1024;
        if (ObjectUtil.isEmpty(totalSize)) {
            return R.ok(userSongsVoList, 0L);
        }
        return R.ok(userSongsVoList, totalSize.longValue());
    }
}
