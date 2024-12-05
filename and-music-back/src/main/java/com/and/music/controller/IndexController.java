package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.dto.UserDto;
import com.and.music.service.GenresService;
import com.and.music.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/index")
@AllArgsConstructor
@Slf4j
public class IndexController {

    private final UsersService usersService;

    private final GenresService genresService;

    // 根据类型获取分类列表
    @GetMapping("/getGenresList/{type}")
    public R getGenresList(@PathVariable("type") Integer type) {
        return genresService.getGenresList(type);
    }

    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto, HttpSession session) {
        log.info("用户名: {}, 密码: {}", userDto.getUserName(), userDto.getPassword());
        return usersService.checkUser(userDto, session);
    }
    @PostMapping("/register")
    public R register(@RequestBody UserDto userDto) {
        return usersService.register(userDto);
    }

    // 登出
    @GetMapping("/logout")
    public R logout(HttpSession session) {
        session.removeAttribute("user");
        return R.ok();
    }
    @GetMapping("/test")
    public R getUserById(Integer userId) {
        return R.fail("test");
    }
}
