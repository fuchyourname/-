package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UsersService usersService;

    @GetMapping("/getUserPages")
    public R getUserPages() {
        List<Users> users = usersService.list();
        return new R(true, "success", users, null);
    }

    @GetMapping("/getUserById")
    public R getUserById(Integer userId) {
        Users user = usersService.getById(userId);
        return new R(true, "success", user, null);
    }

    @GetMapping("/deleteUserById")
    public R deleteUserById(Integer userId) {
        boolean result = usersService.removeById(userId);
        return new R(result, result ? "success" : "fail", null, null);
    }
}
