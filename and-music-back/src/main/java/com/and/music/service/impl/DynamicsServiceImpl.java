package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.*;
import com.and.music.dto.DynamicsDto;
import com.and.music.mapper.*;
import com.and.music.service.CommentsService;
import com.and.music.service.DynamicsService;
import com.and.music.utils.UserContext;
import com.and.music.vo.CommentVo;
import com.and.music.vo.DynamicsVo;
import com.and.music.vo.SongVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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
    @Resource
    private CommentsMapper commentsMapper;
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
                    .setComments(new ArrayList<>())
                    .setCreateTime(dynamics.getCreateTime());
        }).collect(Collectors.toList());
        List<CommentVo> commentVoList = getComments(dynamicsList.stream().
                map(Dynamics::getDynamicId).collect(Collectors.toList()));
        Map<Integer, List<CommentVo>> commentVoMap = new HashMap<>();
        commentVoList.forEach(commentVo -> {
            if (ObjectUtil.isEmpty(commentVoMap.get(commentVo.getOtherId()))) {
                List<CommentVo> commentVoList1 = new ArrayList<>();
                commentVoList1.add(commentVo);
                commentVoMap.put(commentVo.getOtherId(), commentVoList1);
            }else {
                commentVoMap.get(commentVo.getOtherId()).add(commentVo);
            }
        });
        dynamicsVoList.forEach(dynamicsVo -> {
            if (ObjectUtil.isEmpty(commentVoMap.get(dynamicsVo.getDynamicId()))) {
                dynamicsVo.setComments(new ArrayList<>());
            }else
                dynamicsVo.setComments(commentVoMap.get(dynamicsVo.getDynamicId()));
        });
        // 根据创建时间排序, 创建时间最近的排在前面
        dynamicsVoList.sort(Comparator.comparing(DynamicsVo::getCreateTime).reversed());
        return R.ok(dynamicsVoList);
    }

    // 获取动态评论
    private List<CommentVo> getComments(List<Integer> dynamicIds) {

        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Comments::getOtherId, dynamicIds)
                .eq(Comments::getType, 5);
        List<Comments> commentsList = commentsMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(commentsList)) {
            return new ArrayList<>();
        }
        List<Integer> commentIds = commentsList.stream().
                map(Comments::getCommentId).collect(Collectors.toList());
        List<Integer> newCommentIds = new ArrayList<>();
        List<Integer> allCommentIds = new ArrayList<>();
        allCommentIds.addAll(commentIds);
        getComments(commentIds, newCommentIds, allCommentIds);
        if (ObjectUtil.isEmpty(allCommentIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Comments> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Comments::getCommentId, allCommentIds);
        List<Comments> commentsList1 = commentsMapper.selectList(queryWrapper1);
        List<CommentVo> commentVoList = turnToCommentVo(commentsList1);
        return groupByParentId(commentVoList);
    }
    private void getComments(List<Integer> oldCommentIds, List<Integer> newCommentIds,
                             List<Integer> allCommentIds
                             ) {
        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Comments::getOtherId, oldCommentIds);
        queryWrapper.eq(Comments::getType, 4);
        List<Comments> commentsList = commentsMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(commentsList)) {
            return;
        }
        for (Comments comments : commentsList) {
            newCommentIds.add(comments.getCommentId());
        }
        oldCommentIds.clear();
        allCommentIds.addAll(newCommentIds);
        getComments(newCommentIds, oldCommentIds, allCommentIds);
    }

    private List<CommentVo> turnToCommentVo(List<Comments> commentsList) {
        List<Integer> userIds = commentsList.stream().map(Comments::getUserId).collect(Collectors.toList());
        List<Users> usersList = usersMapper.selectBatchIds(userIds);
        return commentsList.stream().map(comments -> {
            return new CommentVo()
                    .setCommentId(comments.getCommentId())
                    .setUserId(comments.getUserId())
                    .setUserName(usersList.stream().filter(users -> users.getUserId().equals(comments.getUserId())).findFirst().get().getUserName())
                    .setUserPic(usersList.stream().filter(users -> users.getUserId().equals(comments.getUserId())).findFirst().get().getPicUrl())
                    .setContent(comments.getContent())
                    .setLikeCount(0)
                    .setParentId(comments.getParentId())
                    .setOtherId(comments.getOtherId())
                    .setReplyCount(0)
                    .setCreateTime(comments.getCreateTime());
        }).collect(Collectors.toList());
    }

    private List<CommentVo> groupByParentId(List<CommentVo> commentVoList) {
        /*
         * 1. 获取所有父级评论
         * 2. 获取所有子级评论
         * 3. 将子级评论添加到父级评论中
         * 4. 返回父级评论
         */
        List<CommentVo> parentCommentList = commentVoList.stream().filter(commentVo -> commentVo.getParentId() == 0).collect(Collectors.toList());
        List<CommentVo> childCommentList = commentVoList.stream().filter(commentVo -> commentVo.getParentId() != 0).collect(Collectors.toList());
        for (CommentVo parentComment : parentCommentList) {
            List<CommentVo> childComment =
                    childCommentList.stream().filter(commentVo ->
                            commentVo.getParentId().equals(parentComment.getCommentId())).
                            collect(Collectors.toList());
            if (ObjectUtil.isEmpty(childComment)) {
                parentComment.setReplyCount(0);
                parentComment.setComments(new ArrayList<>());
            }
            parentComment.setReplyCount(childComment.size());
            parentComment.setComments(childComment);
            childComment.forEach(commentVo -> {
                commentVo.setReplyCount(0);
                commentVo.setComments(new ArrayList<>());
            });
        }
        return parentCommentList;
    }
}
