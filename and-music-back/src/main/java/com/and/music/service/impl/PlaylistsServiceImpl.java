package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Playlists;
import com.and.music.domain.Users;
import com.and.music.dto.PlaylistDto;
import com.and.music.mapper.PlaylistSongsMapper;
import com.and.music.mapper.PlaylistsMapper;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.PlaylistsService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.PlaylistVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author and
* @description 针对表【playlists】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class PlaylistsServiceImpl extends ServiceImpl<PlaylistsMapper, Playlists>
    implements PlaylistsService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private MinioUtils minioUtils;

    @Resource
    private PlaylistSongsMapper playlistSongsMapper;

    @Override
    @Transactional
    public R addPlaylists(PlaylistDto playlistDto) {
        /*
         * 1、判断歌单名称是否重复
         * 2、判断歌单封面是否上传成功
         * 3、保存歌单信息
         * 4、返回结果
         */
        if (ObjectUtil.isEmpty(playlistDto)) {
            return R.fail("参数错误");
        }
        String name = playlistDto.getName();
        if (ObjectUtil.isEmpty(name)) {
            return R.fail("歌单名称不能为空");
        }
        List<Playlists> playlistsList =
                list(new QueryWrapper<Playlists>().eq("name", name));
        if (ObjectUtil.isNotEmpty(playlistsList)) {
            return R.fail("歌单名称重复");
        }
        Playlists playlists = new Playlists();
        playlists.setName(name);
        playlists.setDescription(playlistDto.getDescription());
//        playlists.setUserId(UserContext.getUser().getUserId());
        playlists.setUserId(2);
        this.baseMapper.insert(playlists);

        try {
            MultipartFile pl = playlistDto.getImage();
            if (pl != null) {
                // 上传歌曲图片
                String bucketName = "music";
                String objectPath = "cover/" + playlistDto.getType() + "/" + playlists.getPlaylistId() + ".jpg";

                if (!minioUtils.bucketExists(bucketName)) {
                    minioUtils.createBucket(bucketName);
                }

                String coverUrl = minioUtils.putObject(bucketName, objectPath, pl);

                playlists.setImageUrl(coverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
        this.baseMapper.updateById(playlists);

        playlistSongsMapper.insertBatch(playlists.getPlaylistId(), playlistDto.getSongIds());

        return R.ok();
    }

    @Override
    public R getSongs(Integer playlistId) {
        return null;
    }

    @Override
    public R getRecommendPlaylists() {
        /**
         * 1、获取歌单表中前十条记录
         * 2. 根据用户id获取用户信息
         * 3. 构造返回结果
         */
        List<Playlists> playlistsList =
                this.list(new QueryWrapper<Playlists>().last("limit 10"));

        if (ObjectUtil.isEmpty(playlistsList)) {
            return R.ok("暂无推荐歌单");
        }
        List<Integer> userIds = playlistsList.stream().
                map(Playlists::getUserId).collect(Collectors.toList());
        if (ObjectUtil.isEmpty(userIds)) {
            return R.fail("获取推荐歌单失败");
        }
        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        List<PlaylistVo> playlistVoList = playlistsList.stream().map(playlists -> {
           return new PlaylistVo()
                   .setPlaylistId(playlists.getPlaylistId())
                   .setUserName(usersList.stream().filter(users -> users.getUserId().equals(playlists.getUserId())).findFirst().get().getUserName())
                   .setName(playlists.getName())
                   .setDescription(playlists.getDescription())
                   .setImageUrl(playlists.getImageUrl())
                   .setSongCount(playlists.getSongCount());
        }).collect(Collectors.toList());

        return R.ok(playlistVoList);
    }

    @Override
    public R getPlaylistDetail(Integer playlistId) {

        if (ObjectUtil.isEmpty(playlistId)) {
            return R.fail("参数错误");
        }
        Playlists playlists = getById(playlistId);
        if (ObjectUtil.isNotEmpty(playlists)) {
            return R.ok(playlists);
        }
        return R.fail("未找到该歌单");
    }
}




