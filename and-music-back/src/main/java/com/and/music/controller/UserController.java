package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.DynamicsDto;
import com.and.music.dto.PageInfo;
import com.and.music.dto.UserDto;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.DynamicsService;
import com.and.music.service.FollowService;
import com.and.music.service.UserSongsService;
import com.and.music.service.UsersService;
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

    // 关注歌手
    @PostMapping("/addFollowSinger")
    public R addFollowSinger(@RequestParam("followSingerId") Integer followSingerId) {
        return followService.addSinger(followSingerId);
    }
    // 取消关注歌手
    @PostMapping("/removeFollowSinger")
    public R removeFollowSinger(@RequestParam("followSingerId") Integer followSingerId) {
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
    public R updateUser(@RequestParam("userId") Integer userId,
                        @RequestParam("userName") String userName,
                        @RequestParam("description") String description,
                        @RequestParam("nationality") String nationality,
                        @RequestParam("email") String email,
                        @RequestParam("type") Integer type,
                        @RequestParam(name = "pic", required = false) MultipartFile pic
                        ) {
        UserDto userDto = new UserDto()
                .setUserId(userId)
                .setUserName(userName)
                .setDescription(description)
                .setNationality(nationality)
                .setEmail(email)
                .setType(type)
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
