package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.dto.PageInfo;
import com.and.music.dto.UserDto;
import com.and.music.service.GenresService;
import com.and.music.service.UsersService;
import com.and.music.utils.MinioUtils;
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

    private final MinioUtils minioUtils;

    private final GenresService genreService;

    // 分页获取分类信息
    @PostMapping("/getGenresPage")
    public R getGenresList(@RequestBody PageInfo pageInfo) {
        return genreService.getGenresPage(pageInfo);
    }

    // 更新用户信息
    @PostMapping("/updateUser")
    public R updateUser(@RequestBody UserDto userDto) {
        return usersService.updateUser(userDto);
    }
    // 分页
    @PostMapping("/getUserPage")
    public R getUserPage(@RequestBody PageInfo pageInfo) {
        return usersService.getUserPage(pageInfo);
    }

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

    // 分页获取minio下的所有图片
    @PostMapping("/getImgList")
    public R getImgList(@RequestBody PageInfo pageInfo) {

        if (minioUtils.bucketExists("music")) {
            String objectName = "cover/" + pageInfo.getType();
            return R.ok(minioUtils.getAllImgList("music"
                    , objectName));
        }
        return null;
    }
}
