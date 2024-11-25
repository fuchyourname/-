package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.dto.UserDto;
import com.and.music.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/index")
@AllArgsConstructor
@Slf4j
public class IndexController {

    private final UsersService usersService;

    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto) {
        log.info("用户名: {}, 密码: {}", userDto.getUserId(), userDto.getPassword());
        return usersService.checkUser(userDto);
    }
    @PostMapping("/register")
    public R register(@RequestBody UserDto userDto) {
        return usersService.register(userDto);
    }

    @GetMapping("/test")
    public R getUserById(Integer userId) {
        return R.fail("test");
    }
}
