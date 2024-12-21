package com.and.music.service;

import com.and.music.common.R;
import com.and.music.domain.Comments;
import com.and.music.dto.CommentDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【comments】的数据库操作Service
* @createDate 2024-10-14 15:31:30
*/
public interface CommentsService extends IService<Comments> {

    // 给歌单评论
    // 给歌曲评论
    // 给动态评论
    public R addComments(CommentDto commentDto);
    // 给评论回复
    public R addReply(CommentDto commentDto);
}
