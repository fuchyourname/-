package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.DynamicsDto;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.DynamicsService;
import com.and.music.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UsersService usersService;

    private final DynamicsService dynamicsService;

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
