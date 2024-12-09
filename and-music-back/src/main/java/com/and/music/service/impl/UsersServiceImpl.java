package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.UserDto;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.UsersService;
import com.and.music.utils.UserContext;
import com.and.music.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
* @author and
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-10-14 15:31:31
*/
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

    @Override
    public R checkUser(UserDto userDto, HttpSession session) {

        if (ObjectUtil.isEmpty(userDto)) {
            return R.fail("参数错误");
        }
        String userName = userDto.getUserName();
        if (ObjectUtil.isNotEmpty(userName)) {
            Users user = getOne(new LambdaQueryWrapper<Users>().eq(Users::getUserName, userName));
            if (ObjectUtil.isNotEmpty(user) && user.getPassword().equals(userDto.getPassword())) {
                UserVo userVo = new UserVo()
                        .setUserId(user.getUserId())
                        .setUsername(user.getUserName())
                        .setAvatar(user.getPicUrl());
                session.setAttribute("user", user);
                return R.ok(userVo);
            }
            return R.fail("用户名或密码错误");
        }
        return R.fail("用户不存在");
    }

    @Override
    public R register(UserDto userDto) {

        Users user = new Users();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreateUser(userDto.getUserId());
        user.setUpdateUser(userDto.getUserId());

        if (save(user)) {
            return R.ok();
        }
        return R.fail("注册失败");
    }

    @Override
    public R getUserByName(String userName) {
        return null;
    }
}




