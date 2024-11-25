package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.UserDto;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-10-14 15:31:31
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

    @Override
    public R checkUser(UserDto userDto) {

        if (ObjectUtil.isEmpty(userDto)) {
            return R.fail("参数错误");
        }
        Integer userId = userDto.getUserId();
        if (ObjectUtil.isNotEmpty(userId)) {
            Users user = getById(userId);
            if (ObjectUtil.isNotEmpty(user) && user.getPassword().equals(userDto.getPassword())) {
                return R.ok();
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
}



