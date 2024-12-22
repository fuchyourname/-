package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.UserSongs;
import com.and.music.domain.Users;
import com.and.music.dto.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserSongsService extends IService<UserSongs> {

    // 下载歌曲
    R download(Integer songId);
    // 添加歌曲
    R addUserSongs(MultipartFile File);

    // 分页获取用户歌曲
    R getUserSongsPage(PageInfo pageInfo);
}
