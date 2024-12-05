package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Artists;
import com.and.music.domain.Follow;
import com.and.music.domain.Users;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.mapper.FollowMapper;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.FollowService;
import com.and.music.utils.UserContext;
import com.and.music.vo.SingerVo;
import com.and.music.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Resource
    private UsersMapper usersMapper;
    @Resource
    private ArtistsMapper artistsMapper;
    @Override
    public R addFollow(Integer followUserId) {

        if (ObjectUtil.isEmpty(followUserId)) {
            return R.fail("参数错误");
        }

        Follow follow = new Follow()
                .setFollowUserId(followUserId)
                .setType(2)
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setUserId(UserContext.getUser().getUserId());

        if (this.baseMapper.insert(follow) > 0) {
            return R.ok();
        }
        return R.fail("关注失败");
    }

    @Override
    public R removeFollow(Integer followUserId) {

        if (ObjectUtil.isEmpty(followUserId)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getFollowUserId, followUserId)
                .eq(Follow::getUserId, UserContext.getUser().getUserId())
                .eq(Follow::getType, 2);

        if (this.baseMapper.delete(queryWrapper) > 0) {
            return R.ok();
        }
        return R.fail("取消关注失败");
    }

    @Override
    public R getFollowList() {

        List<Follow> follows = this.baseMapper.selectList(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getUserId, UserContext.getUser().getUserId())
                .eq(Follow::getType, 2));

        if (ObjectUtil.isEmpty(follows)) {
            return R.ok();
        }
        List<Integer> userIds = follows.stream().
                map(Follow::getFollowUserId).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(userIds)) {
            return R.ok();
        }
        List<Users> usersList = usersMapper.selectBatchIds(userIds);

        List<UserVo> userVoList = usersList.stream().map(users -> {

            return new UserVo()
                    .setUserId(users.getUserId())
                    .setUsername(users.getUserName())
                    .setAvatar(users.getPicUrl())
                    .setDescription(users.getDescription())
                    .setEmail(users.getEmail());
        }).collect(Collectors.toList());

        return R.ok(userVoList);
    }

    @Override
    public R addSinger(Integer singerId) {

        if (ObjectUtil.isEmpty(singerId)) {
            return R.fail("参数错误");
        }

        if (this.baseMapper.insert(new Follow()
                .setFollowUserId(singerId)
                .setType(1)
                .setCreateUser(UserContext.getUser().getUserId())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setUserId(UserContext.getUser().getUserId())) > 0) {
            return R.ok();
        }
        return R.fail("关注歌手失败");
    }

    @Override
    public R removeSinger(Integer singerId) {

        if (ObjectUtil.isEmpty(singerId)) {
            return R.fail("参数错误");
        }

        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Follow::getFollowUserId, singerId)
                .eq(Follow::getUserId, UserContext.getUser().getUserId())
                .eq(Follow::getType, 1);

        if (this.baseMapper.delete(queryWrapper) > 0) {
            return R.ok();
        }
        return R.fail("取消关注歌手失败");
    }

    @Override
    public R getSingerList() {

        List<Follow> follows = this.baseMapper.selectList(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getUserId, UserContext.getUser().getUserId())
                .eq(Follow::getType, 1));

        if (ObjectUtil.isEmpty(follows)) {
            return R.ok();
        }

        List<Integer> singerIds = follows.stream().
                map(Follow::getFollowUserId).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(singerIds)) {
            return R.ok();
        }
        List<Artists> artistsList = artistsMapper.selectBatchIds(singerIds);

        return R.ok(artistsList.stream().map(artists -> {
            return new SingerVo()
                    .setArtistId(artists.getArtistId())
                    .setName(artists.getName())
                    .setBio(artists.getBio())
                    .setPicUrl(artists.getPicUrl())
                    .setNationality(artists.getNationality());
        }).collect(Collectors.toList()));
    }
}
