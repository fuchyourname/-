package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.CommentDto;
import com.and.music.dto.DynamicsDto;
import com.and.music.dto.PageInfo;
import com.and.music.dto.UserDto;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UsersService usersService;

    private final DynamicsService dynamicsService;

    private final UserSongsService userSongsService;

    private final FollowService followService;

    private final CommentsService commentsService;

    // 用户评论
    @PostMapping("/addComments")
    public R addComments(@RequestBody CommentDto commentDto) {
        return commentsService.addComments(commentDto);
    }

    // 关注歌手
    @GetMapping("/addFollowSinger/{followSingerId}")
    public R addFollowSinger(@PathVariable("followSingerId") Integer followSingerId) {
        return followService.addSinger(followSingerId);
    }
    // 取消关注歌手
    @GetMapping("/removeFollowSinger/{followSingerId}")
    public R removeFollowSinger(@PathVariable("followSingerId") Integer followSingerId) {
        return followService.removeSinger(followSingerId);
    }

    // 获取用户关注的用户列表
    @GetMapping("/getFollowUserList")
    public R getFollowUserList() {
        return usersService.getFollowUserList();
    }

    // 关注用户
    @PostMapping("/addFollow")
    public R addFollow(@RequestParam("followUserId") Integer followUserId) {
        return followService.addFollow(followUserId);
    }
    // 取消关注用户
    @PostMapping("/removeFollow")
    public R removeFollow(@RequestParam("followUserId") Integer followUserId) {
        return followService.removeFollow(followUserId);
    }

    // 用户下载歌曲
    @GetMapping("/download/{songId}")
    public R download(@PathVariable("songId") Integer songId) {
        return userSongsService.download(songId);
    }

    // 用户上传音乐
    @PostMapping("/uploadSong")
    public R uploadSong(@RequestParam("file") MultipartFile file) {
        return userSongsService.addUserSongs(file);
    }
    // 用户分页获取上传的歌曲
    @PostMapping("/getUserSongs")
    public R getUserSongs(@RequestBody PageInfo pageInfo) {
        return userSongsService.getUserSongsPage(pageInfo);
    }

    // 修改用户信息
    @PostMapping("/updateUser")
    public R saveOrUpdateUser(
            @RequestParam(name = "userId", required = false) Integer userId,
                        @RequestParam("userName") String userName,
                        @RequestParam("description") String description,
                        @RequestParam("nationality") String nationality,
                        @RequestParam("email") String email,
                        @RequestParam(name = "pic", required = false) MultipartFile pic
                        ) {
        UserDto userDto = new UserDto()
                .setUserId(userId)
                .setUserName(userName)
                .setDescription(description)
                .setNationality(nationality)
                .setEmail(email)
                .setPic(pic);
        return usersService.updateUser(userDto);
    }
    // 分页
    @PostMapping("/getUserPage")
    public R getUserPage(@RequestBody PageInfo pageInfo) {
        return usersService.getUserPage(pageInfo);
    }

    // 用户发动态
    @GetMapping("/addDynamics")
    public R addDynamics(
            @RequestParam("content") String content,
            @RequestParam("musicId") Integer musicId) {
        return dynamicsService.addDynamics(new DynamicsDto(content, musicId));
    }
    // 获取用户动态
    @GetMapping("/getDynamics")
    public R getDynamics() {
        return dynamicsService.getDynamicsList();
    }
}
