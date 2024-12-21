package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.PageInfo;
import com.and.music.dto.UserDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
* @author and
* @description 针对表【users】的数据库操作Service
* @createDate 2024-10-14 15:31:31
*/
public interface UsersService extends IService<Users> {

    // 获取用户关注的用户列表
    R getFollowUserList();

    R checkUser(UserDto userDto, HttpSession session);

    R register(UserDto userDto);

    // 新增用户
    R saveUser(UserDto userDto);

    // 修改用户信息
    R updateUser(UserDto userDto);
    // 分页
    R getUserPage(PageInfo pageInfo);
}
