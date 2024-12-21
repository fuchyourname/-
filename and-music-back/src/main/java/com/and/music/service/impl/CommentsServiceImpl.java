package com.and.music.service.impl;

import com.and.music.common.R;
import com.and.music.domain.Comments;
import com.and.music.dto.CommentDto;
import com.and.music.mapper.CommentsMapper;
import com.and.music.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author and
* @description 针对表【comments】的数据库操作Service实现
* @createDate 2024-10-14 15:31:30
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService {
    @Override
    public R addComments(CommentDto commentDto) {
        return null;
    }

    @Override
    public R addReply(CommentDto commentDto) {
        return null;
    }
}




