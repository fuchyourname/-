package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.*;
import com.and.music.dto.DynamicsDto;
import com.and.music.mapper.*;
import com.and.music.service.CommentsService;
import com.and.music.service.DynamicsService;
import com.and.music.utils.UserContext;
import com.and.music.vo.DynamicsVo;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamicsServiceImpl extends ServiceImpl<DynamicsMapper, Dynamics>
        implements DynamicsService {
    @Resource
    private FollowMapper followMapper;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private SongsMapper songsMapper;
    @Resource
    private AlbumsMapper albumsMapper;
    @Resource
    private ArtistsMapper artistsMapper;
    @Override
    public R addDynamics(DynamicsDto dynamicsDto) {

        if (ObjectUtil.isEmpty(dynamicsDto)) {
            return R.fail("参数错误");
        }
        Dynamics dynamics = new Dynamics()
                .setUserId(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setContent(dynamicsDto.getContent())
                .setMusicId(dynamicsDto.getMusicId());

        if (this.baseMapper.insert(dynamics) > 0) {
            return R.ok();
        }
        return R.fail("发布失败");
    }

    @Override
    public R getDynamicsList() {

        // 根据用户id查询关注的用户ID，去重
        // 拿出对应的ID去对应的表中查询相关用户信息
        // 使用ID查询动态
        // 构造返回结果
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getUserId, UserContext.getUser().getUserId());
        List<Follow> list = followMapper.selectList(queryWrapper);

        List<Integer> userIds = list.stream().map(Follow::getFollowUserId).collect(Collectors.toList());
        userIds.add(UserContext.getUser().getUserId());
        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        LambdaQueryWrapper<Dynamics> dynamicsQueryWrapper = new LambdaQueryWrapper<>();
        dynamicsQueryWrapper.in(Dynamics::getUserId, userIds);
        List<Dynamics> dynamicsList = this.baseMapper.selectList(dynamicsQueryWrapper);

        if (ObjectUtil.isEmpty(dynamicsList)) {
            return R.ok();
        }
        List<Integer> songIds = dynamicsList.stream().map(Dynamics::getMusicId).collect(Collectors.toList());

        List<Songs> songsList = songsMapper.selectBatchIds(songIds);

        List<Integer> artistIds = songsList.stream().map(Songs::getArtistId).collect(Collectors.toList());
        List<Integer> albumIds = songsList.stream().map(Songs::getAlbumId).collect(Collectors.toList());
        List<Albums> albumsList = albumsMapper.selectBatchIds(albumIds);
        List<Artists> artistsList = artistsMapper.selectBatchIds(artistIds);
        Map<Integer, Albums> albumsMap = albumsList.stream().collect(Collectors.toMap(Albums::getAlbumId, albums -> albums));
        Map<Integer, Artists> artistsMap = artistsList.stream().collect(Collectors.toMap(Artists::getArtistId, artists -> artists));

        Map<Integer, SongVo> songsMap = songsList.stream().
                collect(Collectors.toMap(Songs::getSongId, song -> {
                    return new SongVo()
                            .setSongId(song.getSongId())
                            .setTitle(song.getTitle())
                            .setAlbum(albumsMap.get(song.getAlbumId()).getTitle())
                            .setArtist(artistsMap.get(song.getArtistId()).getName())
                            .setCoverPath(song.getCoverPath())
                            .setFilePath(song.getFilePath())
                            .setLyricPath(song.getLyricPath());
                }));

        List<DynamicsVo> dynamicsVoList = dynamicsList.stream().map(dynamics -> {
            return new DynamicsVo()
                    .setDynamicId(dynamics.getDynamicId())
                    .setUserName(usersList.stream().filter(users -> users.getUserId().equals(dynamics.getUserId())).findFirst().get().getUserName())
                    .setUserAvatar(usersList.stream().filter(users -> users.getUserId().equals(dynamics.getUserId())).findFirst().get().getPicUrl())
                    .setContent(dynamics.getContent())
                    .setSong(songsMap.get(dynamics.getMusicId()))
                    .setCreateTime(dynamics.getCreateTime());
        }).collect(Collectors.toList());

        return R.ok(dynamicsVoList);
    }
}
