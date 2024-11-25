package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Users;
import com.and.music.dto.UserDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【users】的数据库操作Service
* @createDate 2024-10-14 15:31:31
*/
public interface UsersService extends IService<Users> {

    R checkUser(UserDto userDto);

    R register(UserDto userDto);

}
