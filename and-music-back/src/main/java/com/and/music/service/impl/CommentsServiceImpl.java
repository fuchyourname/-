package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.R;
import com.and.music.domain.Comments;
import com.and.music.dto.CommentDto;
import com.and.music.mapper.CommentsMapper;
import com.and.music.service.CommentsService;
import com.and.music.utils.UserContext;
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

        if (ObjectUtil.isEmpty(commentDto)) {
            return R.fail("评论失败");
        }
        Comments comments = new Comments()
                .setUserId(UserContext.getUser().getUserId())
                .setOtherId(commentDto.getOtherId())
                .setType(commentDto.getType())
                .setParentId(ObjectUtil.isEmpty(commentDto.getParentId()) ? 0 : commentDto.getParentId())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setContent(commentDto.getContent());
        if (this.baseMapper.insert(comments) > 0) {
            return R.ok();
        }
        return R.fail("评论失败");
    }

    @Override
    public R addReply(CommentDto commentDto) {

        if (ObjectUtil.isEmpty(commentDto)) {
            return R.fail("回复失败");
        }
        Comments comments = new Comments()
                .setUserId(UserContext.getUser().getUserId())
                .setOtherId(commentDto.getOtherId())
                .setType(commentDto.getType())
                .setParentId(commentDto.getParentId())
                .setUpdateUser(UserContext.getUser().getUserId())
                .setCreateUser(UserContext.getUser().getUserId())
                .setContent(commentDto.getContent());

        if (this.baseMapper.insert(comments) > 0) {
            return R.ok();
        }
        return R.fail("回复失败");
    }
}




